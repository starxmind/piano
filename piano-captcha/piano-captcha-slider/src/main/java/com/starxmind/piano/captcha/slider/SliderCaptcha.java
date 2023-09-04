package com.starxmind.piano.captcha.slider;

import com.starxmind.piano.captcha.slider.param.ArtworkParam;
import com.starxmind.piano.captcha.slider.param.MainParam;
import com.starxmind.piano.captcha.slider.param.VacancyParam;
import com.starxmind.piano.captcha.slider.result.SliderCaptchaResult;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 滑块验证码
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
public abstract class SliderCaptcha {
    protected MainParam mainParam;

    protected ArtworkParam artworkParam;

    protected VacancyParam vacancyParam;

    public SliderCaptcha() {
        this.mainParam = new MainParam();
        this.artworkParam = new ArtworkParam();
        this.vacancyParam = new VacancyParam();
    }

    public SliderCaptcha(MainParam mainParam, ArtworkParam artworkParam, VacancyParam vacancyParam) {
        this.mainParam = mainParam;
        this.artworkParam = artworkParam;
        this.vacancyParam = vacancyParam;
    }

    /**
     * 执行生成滑块图片
     *
     * @return
     */
    public SliderCaptchaResult run(Image image) {
        int x = RandomUtils.nextInt(vacancyParam.getVacancySize(), artworkParam.getWidth() - vacancyParam.getVacancySize() - mainParam.getMargin());
        int y = RandomUtils.nextInt(mainParam.getMargin(), artworkParam.getHeight() - vacancyParam.getVacancySize() - mainParam.getMargin());
        return run(image, x, y);
    }

    protected abstract SliderCaptchaResult run(Image image, int x, int y);

    /**
     * 执行生成滑块图片
     *
     * @return
     */
    public SliderCaptchaResult run(File file) throws IOException {
        return run(ImageIO.read(file));
    }

    /**
     * 执行生成滑块图片
     *
     * @return
     */
    public SliderCaptchaResult run(URL url) throws IOException {
        return run(ImageIO.read(url));
    }

    /**
     * 绘制拼图块的路径
     *
     * @param xScale x轴放大比例
     * @param yScale y轴放大比例
     * @return
     */
    protected GeneralPath paintBrick(int intX, int intY, double xScale, double yScale) {
        double x = intX * xScale;
        double y = intY * yScale;
        // 直线移动的基础距离
        double hMoveL = vacancyParam.getVacancySize() / 3f * yScale;
        double wMoveL = vacancyParam.getVacancySize() / 3f * xScale;
        GeneralPath path = new GeneralPath();
        path.moveTo(x, y);
        path.lineTo(x + wMoveL, y);
        // 上面的圆弧正东方向0°，顺时针负数，逆时针正数
        path.append(arc(x + wMoveL, y - hMoveL / 2, wMoveL, hMoveL, 180, -180), true);
        path.lineTo(x + wMoveL * 3, y);
        path.lineTo(x + wMoveL * 3, y + hMoveL);
        // 右边的圆弧
        path.append(arc(x + wMoveL * 2 + wMoveL / 2, y + hMoveL, wMoveL, hMoveL, 90, -180), true);
        path.lineTo(x + wMoveL * 3, y + hMoveL * 3);
        path.lineTo(x, y + hMoveL * 3);
        path.lineTo(x, y + hMoveL * 2);
        // 左边的内圆弧
        path.append(arc(x - wMoveL / 2, y + hMoveL, wMoveL, hMoveL, -90, 180), true);
        path.lineTo(x, y);
        path.closePath();
        return path;
    }

    /**
     * 绘制圆形、圆弧或者是椭圆形
     * 正东方向0°，顺时针负数，逆时针正数
     *
     * @param x      左上角的x坐标
     * @param y      左上角的y坐标
     * @param w      宽
     * @param h      高
     * @param start  开始的角度
     * @param extent 结束的角度
     * @return
     */
    protected Arc2D arc(double x, double y, double w, double h, double start, double extent) {
        return new Arc2D.Double(x, y, w, h, start, extent, Arc2D.OPEN);
    }

    /**
     * 透明背景
     *
     * @param bufferedImage
     * @return
     */
    protected BufferedImage translucent(BufferedImage bufferedImage) {
        Graphics2D g = bufferedImage.createGraphics();
        bufferedImage = g.getDeviceConfiguration().createCompatibleImage(bufferedImage.getWidth(), bufferedImage.getHeight(), Transparency.TRANSLUCENT);
        g.dispose();
        return bufferedImage;
    }
}
