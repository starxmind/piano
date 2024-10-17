package com.starxmind.piano.email;

import com.starxmind.piano.email.request.Attachment;
import com.starxmind.piano.email.request.EmailSenderRequest;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Properties;

/**
 * 邮件发送器
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
@RequiredArgsConstructor
public class EmailSender {

    private final EmailSetting emailSetting;

    public void send(EmailSenderRequest request) {
        // 设置邮件服务器的配置属性
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", emailSetting.getHost());
        properties.setProperty("mail.smtp.port", emailSetting.getPort().toString());
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // 获取默认的Session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSetting.getUsername(), emailSetting.getPassword());
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(emailSetting.getUsername(), emailSetting.getNickname()));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(request.getTo()));

            // Set Subject: 头部头字段
            message.setSubject(request.getSubject());

            if (CollectionUtils.isEmpty(request.getAttachments())) {
                // 设置消息体
                setContent(message, request.isHtml(), request.getContent());
            } else {
                // 创建消息部分（邮件正文）
                BodyPart messageBodyPart = new MimeBodyPart();
                setContent(messageBodyPart, request.isHtml(), request.getContent());

                // 创建多部分邮件
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                // 第二部分是附件
                messageBodyPart = new MimeBodyPart();
                for (Attachment attachment : request.getAttachments()) {
                    DataSource source = new FileDataSource(attachment.getPath());
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(attachment.getName()); // 显示的文件名
                    multipart.addBodyPart(messageBodyPart);
                }

                // 将多部分内容添加到消息中
                message.setContent(multipart);
            }

            // 发送消息
            Transport.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setContent(Part part, boolean isHtml, String content) throws MessagingException {
        if (isHtml) {
            part.setContent(content, "text/html;charset=utf-8");
        } else {
            part.setText(content);
        }
    }
}
