package com.starxmind.piano.email.test;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

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
        final int port = 25;
        final String nickname = "心惟科技";
        final String password = "StarXNOTICE@2024!";
        String from = "notification@starxmind.com";
        String to = "raining0807@163.com";

        // 设置邮件服务器的配置属性
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", String.valueOf(port));
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // 获取默认的Session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from, nickname));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("This is the Subject Line!");

//            // 设置消息体
//            message.setText("This is actual message");
            // 创建消息部分（邮件正文）
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("This is the email body text");

            // 创建多部分邮件
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // 第二部分是附件
            messageBodyPart = new MimeBodyPart();
            String filename = "/Users/jianxue/Documents/mock/1703927801785.jpg"; // 附件的路径
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("file.txt"); // 显示的文件名
            multipart.addBodyPart(messageBodyPart);

            // 将多部分内容添加到消息中
            message.setContent(multipart);

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
