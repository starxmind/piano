package com.starxmind.piano.email.test;

import com.google.common.collect.Lists;
import com.starxmind.piano.email.request.Attachment;
import com.starxmind.piano.email.EmailSender;
import com.starxmind.piano.email.request.EmailSenderRequest;
import com.starxmind.piano.email.EmailSetting;
import org.junit.Test;

public class EmailSenderTest {
    @Test
    public void test() {
        EmailSetting emailSetting = new EmailSetting();
        emailSetting.setHost("smtp.mxhichina.com");
        emailSetting.setPort(25);
        emailSetting.setUsername("notification@starxmind.com");
        emailSetting.setPassword("StarXNOTICE@2024!");
        emailSetting.setNickname("心惟科技");
        EmailSender emailSender = new EmailSender(emailSetting);

        Attachment attachment1 = new Attachment("test1", "/Users/jianxue/Documents/mock/1703927801785.jpg");
        Attachment attachment2 = new Attachment("test2", "/Users/jianxue/Documents/mock/1703928614470.jpg");
        emailSender.send(
                EmailSenderRequest.builder()
                        .to("raining0807@163.com")
                        .subject("邮件标题2！")
                        .html(true)
                        .content("<html>" +
                                "<h1>邮件征文邮件征文邮件征文！</h1>" +
                                "</html>")
                        .attachments(
                                Lists.newArrayList(attachment1, attachment2)
                        )
                        .build()
        );
    }
}
