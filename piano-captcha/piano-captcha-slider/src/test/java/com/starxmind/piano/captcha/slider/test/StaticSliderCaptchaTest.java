package com.starxmind.piano.captcha.slider.test;

import com.jhlabs.image.ImageUtils;
import com.starxmind.bass.io.core.FileUtils;
import com.starxmind.piano.captcha.slider.StaticSliderCaptcha;
import com.starxmind.piano.captcha.slider.result.SliderCaptchaResult;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * 静态滑动图片测试
 *
 * @author pizzalord
 * @since 1.0
 */
public class StaticSliderCaptchaTest {
    @Test
    public void test() throws IOException {
        StaticSliderCaptcha sliderCaptcha = new StaticSliderCaptcha();
//        new StaticSliderCaptcha(new MainParam(), new ArtworkParam(), new VacancyParam());
        SliderCaptchaResult captchaResult = sliderCaptcha.run(StaticSliderCaptchaTest.class.getClassLoader().getResource("images/demo.jpg"));
//
//        ImageIO.write(ImageUtils.convertImageToARGB(captchaResult.getArtwork()), "jpg", new File(IOUtils.getSysTmpDir() + "artwork.jpg"));
//        ImageIO.write(ImageUtils.convertImageToARGB(captchaResult.getVacancy()), "jpg", new File(IOUtils.getSysTmpDir() + "vacancy.jpg"));

        ImageIO.write(ImageUtils.convertImageToARGB(captchaResult.getArtwork()), "png", new File(FileUtils.getSysTmpDir() + "artwork.png"));
        ImageIO.write(ImageUtils.convertImageToARGB(captchaResult.getVacancy()), "png", new File(FileUtils.getSysTmpDir() + "vacancy.png"));

        System.out.println(FileUtils.getSysTmpDir());
    }
}
