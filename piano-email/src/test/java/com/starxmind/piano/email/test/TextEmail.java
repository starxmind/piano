package com.starxmind.piano.email.test;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public class TextEmail {
    public static void main(String[] args) {
        final String host = "smtp.mxhichina.com";
        final int port = 80;
        final String username = "心惟科技";
        final String password = "XwNOTICE@1989!";
        String from = "notification@starxmind.com";
        String to = "raining0807@163.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties);

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("This is the Subject Line!");

            // 设置消息体
            message.setText("This is actual message");

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
