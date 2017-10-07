package com.future.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by zhengming on 17/10/2.
 */
@Service(value = "reportMailSender")
public class ReportMailSender {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${mail.receiver}")
    private String[] receivers;
    public void send(String filePath, String fileName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("390241476@qq.com");
            helper.setTo(receivers);
            helper.setSubject(fileName);
            helper.setText("请查收附件:"+fileName);

            FileSystemResource file = new FileSystemResource(filePath+ File.separator+fileName);
            helper.addAttachment(file.getFilename(), file);

        }catch (MessagingException e) {
            throw new MailParseException(e);
        }
        mailSender.send(message);
    }
}
