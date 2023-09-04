package com.starxmind.piano.captcha.slider.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * 底图参数
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class ArtworkParam {
    // artwork begin...
    /**
     * 生成图片的宽度
     */
    private int width = 280;

    /**
     * 生成图片高度
     */
    private int height = 150;

    /**
     * 主图描边的颜色
     */
    private Color artworkBorderColor;

    /**
     * 主图描边线条的宽度
     */
    private float artworkBorderWidth = 5f;
}
