package com.starxmind.piano.captcha.slider.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * 全局参数
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class MainParam {
    /**
     * 边距
     */
    private int margin = 10;

    /**
     * 最高放大倍数,合理的放大倍数可以使图像平滑且提高渲染速度
     * 当isFast为false时，此属性生效
     * 放大倍数越高，生成的图像越平滑，受原始图片大小的影响。
     */
    private double maxRatio = 2;

    /**
     * 画质
     *
     * @see Image#SCALE_DEFAULT
     * @see Image#SCALE_FAST
     * @see Image#SCALE_SMOOTH
     * @see Image#SCALE_REPLICATE
     * @see Image#SCALE_AREA_AVERAGING
     */
    private int imageQuality = Image.SCALE_SMOOTH;

    /**
     * 蒙版
     */
    private Color maskColor = new Color(255, 255, 255, 204);

    /**
     * 是否注重速度
     */
    private boolean concernFast = false;
}
