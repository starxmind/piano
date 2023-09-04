package com.starxmind.piano.captcha.slider.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * 滑块图片处理结果
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class SliderCaptchaResult {
    /**
     * X位移
     */
    private int x;

    /**
     * Y位移
     */
    private int y;

    /**
     * 底图宽度
     */
    private int width;

    /**
     * 地图高度
     */
    private int height;

    /**
     * 底图
     */
    private Image artwork;

    /**
     * 缺图
     */
    private Image vacancy;
}
