package com.starxmind.piano.captcha.slider.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * 图片空缺参数
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class VacancyParam {
    /**
     * 图片空缺长宽
     */
    private int vacancySize = 30;

    /**
     * 图片空缺描边颜色
     */
    private Color vacancyBorderColor;

    /**
     * 图片空缺描边线条的宽度
     */
    private float vacancyBorderWidth = 2.5f;
}
