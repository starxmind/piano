package com.starxmind.piano.captcha.words.test;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.impl.WaterRipple;
import com.starxmind.bass.io.core.FileUtils;
import com.starxmind.piano.captcha.words.KaptCha;
import com.starxmind.piano.captcha.words.KaptchaProperty;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 验证码测试
 *
 * @author pizzalord
 * @since 1.0
 */
public class KaptchaTest {
    @Test
    public void testClass() {
        System.out.println(DefaultKaptcha.class.getName());
    }

    @Test
    public void testBase64Image() throws IOException {
        KaptchaProperty kaptchaProperty = new KaptchaProperty();
        kaptchaProperty.setObscurificatorImpl(WaterRipple.class);
        KaptCha kaptCha = new KaptCha(kaptchaProperty);
//        String kaptChaText = kaptCha.createText();
        String kaptChaText = "3 + 5 =";
        String base64 = kaptCha.createImageAsBase64(kaptChaText, "jpg");
        System.out.println(base64);
    }

    @Test
    public void testStreamImage() throws IOException {
        String filepath = FileUtils.getSysTmpDir() + "kaptcha.png";

        KaptchaProperty kaptchaProperty = new KaptchaProperty();
        KaptCha kaptCha = new KaptCha(kaptchaProperty);
        FileOutputStream out = new FileOutputStream(filepath);

        kaptCha.createImageAsStream(kaptCha.createText(), "png", out);

        System.out.println(filepath);
    }
}
