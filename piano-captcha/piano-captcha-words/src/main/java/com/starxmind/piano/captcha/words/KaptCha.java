package com.starxmind.piano.captcha.words;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.starxmind.bass.io.core.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import static com.google.code.kaptcha.Constants.*;

/**
 * KaptCha
 *
 * @author pizzalord
 * @since 1.0
 */
public class KaptCha {
    private DefaultKaptcha defaultKaptcha;

    public KaptCha(KaptchaProperty kaptchaProperty) {
        defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(toConfig(kaptchaProperty));
    }

    /**
     * 生成随机文本
     *
     * @return 随机文本
     */
    public String createText() {
        return defaultKaptcha.createText();
    }

    /**
     * 使用给定文本生成base64格式的图像
     *
     * @param text   给定文本
     * @param format 图像格式
     * @return
     * @throws IOException IO异常
     */
    public String createImageAsBase64(String text, String format) throws IOException {
        BufferedImage image = defaultKaptcha.createImage(text);
        return ImageUtils.readImageAsBase64(image, format);
    }

    /**
     * 使用给定文本写到输出流中
     *
     * @param text   给定文本
     * @param format 图像格式
     * @param out    输出流
     * @throws IOException IO异常
     */
    public void createImageAsStream(String text, String format, OutputStream out) throws IOException {
        BufferedImage image = defaultKaptcha.createImage(text);
        ImageIO.write(image, format, out);
    }

    private Config toConfig(KaptchaProperty kaptchaProperty) {
        Properties properties = new Properties();
        // 图片边框
        properties.setProperty(KAPTCHA_BORDER, kaptchaProperty.isBorderEnable() ? "yes" : "no");
        // 边框颜色
        properties.setProperty(KAPTCHA_BORDER_COLOR, kaptchaProperty.getBorderColor());
        // 边框厚度
        properties.setProperty(KAPTCHA_BORDER_THICKNESS, String.valueOf(kaptchaProperty.getBorderThickness()));

        // 图片宽
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, String.valueOf(kaptchaProperty.getImageWidth()));
        // 图片高
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, String.valueOf(kaptchaProperty.getImageHeight()));

        // 图片实现类
        properties.setProperty(KAPTCHA_PRODUCER_IMPL, kaptchaProperty.getProducerImpl().getName());
        // 文本实现类
        properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, kaptchaProperty.getTextproducerImpl().getName());

        // 文本集合，验证码值从此集合中获取
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_STRING, kaptchaProperty.getTextproducerCharString());
        // 验证码长度
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, String.valueOf(kaptchaProperty.getTextproducerCharLength()));

        // 字体
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, kaptchaProperty.getTextproducerFontNames());
        // 字体颜色
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, kaptchaProperty.getTextproducerFontColor());
        // 文字间隔
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, String.valueOf(kaptchaProperty.getTextproducerCharSpace()));

        // 干扰实现类
        properties.setProperty(KAPTCHA_NOISE_IMPL, kaptchaProperty.getNoiseImpl().getName());
        // 干扰颜色
        properties.setProperty(KAPTCHA_NOISE_COLOR, kaptchaProperty.getNoiseColor());
        /*
         * 干扰图片样式
         * 水纹 com.google.code.kaptcha.impl.WaterRipple
         * 鱼眼 com.google.code.kaptcha.impl.FishEyeGimpy
         * 阴影 com.google.code.kaptcha.impl.ShadowGimpy
         */
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, kaptchaProperty.getObscurificatorImpl().getName());

        // 背景实现类
        properties.setProperty(KAPTCHA_BACKGROUND_IMPL, kaptchaProperty.getBackgroundImpl().getName());
        // 背景颜色渐变，结束颜色
        properties.setProperty(KAPTCHA_BACKGROUND_CLR_TO, kaptchaProperty.getBackgroundClrTo());

        // 文字渲染器
        properties.setProperty(KAPTCHA_WORDRENDERER_IMPL, kaptchaProperty.getWordrendererImpl().getName());
        Config config = new Config(properties);
        return config;
    }
}
