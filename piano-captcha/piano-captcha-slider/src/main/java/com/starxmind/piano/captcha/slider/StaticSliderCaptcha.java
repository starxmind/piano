package com.starxmind.piano.captcha.slider;

import com.jhlabs.image.ImageUtils;
import com.jhlabs.image.InvertAlphaFilter;
import com.jhlabs.image.ShadowFilter;
import com.starxmind.bass.math.DivideOp;
import com.starxmind.piano.captcha.slider.param.ArtworkParam;
import com.starxmind.piano.captcha.slider.param.MainParam;
import com.starxmind.piano.captcha.slider.param.VacancyParam;
import com.starxmind.piano.captcha.slider.result.SliderCaptchaResult;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

/**
 * 静态滑动验证码
 *
 * @author pizzalord
 * @since 1.0
 */
public class StaticSliderCaptcha extends SliderCaptcha {
    public StaticSliderCaptcha() {
        super();
    }

    public StaticSliderCaptcha(MainParam mainParam, ArtworkParam artworkParam, VacancyParam vacancyParam) {
        super(mainParam, artworkParam, vacancyParam);
    }

    @Override
    protected SliderCaptchaResult run(Image image, int x, int y) {
        // 缩略图
        Image thumbnail;
        GeneralPath path;
        int realW = image.getWidth(null);
        int realH = image.getHeight(null);
        int w = realW, h = realH;
        double wScale = 1, hScale = 1;
        // 如果原始图片比执行的图片还小，则先拉伸再裁剪
        boolean isFast = super.mainParam.isConcernFast() || w < super.artworkParam.getWidth() || h < super.artworkParam.getHeight();
        if (isFast) {
            // 缩放，使用平滑模式
            thumbnail = image.getScaledInstance(super.artworkParam.getWidth(), super.artworkParam.getHeight(), super.mainParam.getImageQuality());
            path = paintBrick(x, y, 1, 1);
            w = super.artworkParam.getWidth();
            h = super.artworkParam.getHeight();
        } else {
            // 缩小到一定的宽高，保证裁剪的圆润
            boolean flag = false;
            if (realW > super.artworkParam.getWidth() * super.mainParam.getMaxRatio()) {
                // 不超过最大倍数且不超过原始图片的宽
                w = Math.min((int) (super.artworkParam.getWidth() * super.mainParam.getMaxRatio()), realW);
                flag = true;
            }
            if (realH > super.artworkParam.getHeight() * super.mainParam.getMaxRatio()) {
                h = Math.min((int) (super.artworkParam.getHeight() * super.mainParam.getMaxRatio()), realH);
                flag = true;
            }
            if (flag) {
                // 若放大倍数生效，则缩小图片至最高放大倍数，再进行裁剪
                thumbnail = image.getScaledInstance(w, h, super.mainParam.getImageQuality());
            } else {
                thumbnail = image;
            }
            hScale = DivideOp.divide(h, super.artworkParam.getHeight()).doubleValue();
            wScale = DivideOp.divide(w, super.artworkParam.getWidth()).doubleValue();
            path = paintBrick(x, y, wScale, hScale);
        }

        // 创建阴影过滤器
        float radius = 5 * ((float) w / super.artworkParam.getWidth()) * (float) wScale;
        ShadowFilter shadowFilter = new ShadowFilter(radius, 2 * (float) wScale, -1 * (float) hScale, 0.8f);

        // 创建空白的图片
        BufferedImage artworkBf = translucent(new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB));
        BufferedImage localVacancyBf = translucent(new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB));
        // 画小图
        Graphics2D vg = localVacancyBf.createGraphics();
        // 抗锯齿
        vg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置画图路径范围
        vg.setClip(path);
        // 将区域中的图像画到小图中
        vg.drawImage(thumbnail, null, null);
        //描边
        if (super.getVacancyParam().getVacancyBorderColor() != null) {
            vg.setColor(super.getVacancyParam().getVacancyBorderColor());
            vg.setStroke(new BasicStroke(super.getVacancyParam().getVacancyBorderWidth()));
            vg.draw(path);
        }
        // 释放图像
        vg.dispose();

        // 画大图
        // 创建画笔
        Graphics2D g = artworkBf.createGraphics();
        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 画上图片
        g.drawImage(thumbnail, null, null);
        // 设置画图路径范围
        g.setClip(path);
        // 填充缺口透明度 颜色混合,不透明在上
        g.setComposite(AlphaComposite.SrcAtop);
        // 填充一层白色的透明蒙版，透明度越高，白色越深 alpha：0-255
        g.setColor(super.mainParam.getMaskColor());
        g.fill(path);
        //描边
        if (super.artworkParam.getArtworkBorderColor() != null) {
            g.setColor(super.artworkParam.getArtworkBorderColor());
            g.setStroke(new BasicStroke(super.artworkParam.getArtworkBorderWidth()));
            g.draw(path);
        }
        // 画上基于小图的内阴影，先反转alpha通道，然后创建阴影
        g.drawImage(shadowFilter.filter(new InvertAlphaFilter().filter(localVacancyBf, null), null), null, null);
        // 释放图像
        g.dispose();

        // 裁剪掉多余的透明背景
        int left = 1;
        localVacancyBf = ImageUtils.getSubimage(localVacancyBf, (int) (x * wScale - left), 0, (int) Math.ceil(path.getBounds().getWidth() + radius) + left, h);

        Image artwork, vacancy;
        if (isFast) {
            // 添加阴影
            vacancy = shadowFilter.filter(localVacancyBf, null);
            artwork = artworkBf;
        } else {
            // 小图添加阴影
            localVacancyBf = shadowFilter.filter(localVacancyBf, null);
            // 大图缩放
            artwork = artworkBf.getScaledInstance(super.artworkParam.getWidth(), super.artworkParam.getHeight(), super.mainParam.getImageQuality());
            // 缩放时，需要加上阴影的宽度，再除以放大比例
            vacancy = localVacancyBf.getScaledInstance((int) ((path.getBounds().getWidth() + radius) / wScale), super.artworkParam.getHeight(), super.mainParam.getImageQuality());
        }

        return SliderCaptchaResult.builder()
                .x(x)
                .y(y)
                .width(super.artworkParam.getWidth())
                .height(super.artworkParam.getHeight())
                .artwork(artwork)
                .vacancy(vacancy)
                .build();
    }
}
