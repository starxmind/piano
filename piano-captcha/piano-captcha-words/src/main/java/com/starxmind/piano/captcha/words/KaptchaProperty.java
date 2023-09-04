package com.starxmind.piano.captcha.words;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultBackground;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.DefaultNoise;
import com.google.code.kaptcha.impl.ShadowGimpy;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.text.impl.DefaultTextCreator;
import com.google.code.kaptcha.text.impl.DefaultWordRenderer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码属性
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class KaptchaProperty {
    /**
     * 图片边框，合法值：yes , no
     */
    private boolean borderEnable = false;

    /**
     * 边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.
     */
    private String borderColor = "black";

    /**
     * 边框厚度
     */
    private int borderThickness = 1;

    /**
     * 图片宽
     */
    private int imageWidth = 200;

    /**
     * 图片高
     */
    private int imageHeight = 50;

    /**
     * 图片实现类
     *
     * @see com.google.code.kaptcha.impl.DefaultKaptcha
     */
    private Class<? extends Producer> producerImpl = DefaultKaptcha.class;

    /**
     * 文本实现类
     *
     * @see com.google.code.kaptcha.text.impl.DefaultTextCreator
     */
    private Class<? extends TextProducer> textproducerImpl = DefaultTextCreator.class;

    /**
     * 文本集合，验证码值从此集合中获取
     */
    private String textproducerCharString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 验证码长度
     */
    private int textproducerCharLength = 4;

    /**
     * 字体
     */
    private String textproducerFontNames = "宋体";

    /**
     * 字体颜色
     */
    private String textproducerFontColor = "black";

    /**
     * 文字间隔
     */
    private int textproducerCharSpace = 5;

    /**
     * 干扰实现类
     *
     * @see com.google.code.kaptcha.impl.DefaultNoise
     */
    private Class<? extends NoiseProducer> noiseImpl = DefaultNoise.class;

    /**
     * 干扰颜色
     */
    private String noiseColor = "blue";

    /**
     * 干扰图片样式
     *
     * @see com.google.code.kaptcha.impl.WaterRipple 水纹
     * @see com.google.code.kaptcha.impl.FishEyeGimpy 鱼眼
     * @see com.google.code.kaptcha.impl.ShadowGimpy 阴影
     */
    private Class<? extends GimpyEngine> obscurificatorImpl = ShadowGimpy.class;

    /**
     * 背景实现类
     *
     * @see com.google.code.kaptcha.impl.DefaultBackground
     */
    private Class<? extends BackgroundProducer> backgroundImpl = DefaultBackground.class;

    /**
     * 背景颜色渐变，结束颜色
     */
    private String backgroundClrTo = "white";

    /**
     * 文字渲染器
     *
     * @see com.google.code.kaptcha.text.impl.DefaultWordRenderer
     */
    private Class<? extends WordRenderer> wordrendererImpl = DefaultWordRenderer.class;
}
