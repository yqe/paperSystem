package com.nju.paperSystem.config;


import com.nju.paperSystem.entity.email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Component
public class emailConfig {

    @Value("${stmp.host}")
    private String host;
    @Value("${stmp.account}")
    private String account;
    @Value("${stmp.password}")
    private String password;
    @Value("${mail.smtp.auth}")
    private String isAuth;
    @Value("${mail.smtp.timeout}")
    private String outTime;
    @Value("${mail.smtp.port}")
    private String port;
    @Value("${mail.smtp.ssl.enable}")
    private String ssl;

    @Bean(name = "JavaMailSenderImpl")
    public JavaMailSenderImpl getMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setUsername(account);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(Integer.valueOf(port));
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", isAuth);
        properties.put("mail.smtp.ssl.enable", Boolean.valueOf(ssl));
        properties.put("mail.smtp.timeout", outTime);
        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    public void sendMail(email email, String studentEmail, String fileName) throws MessagingException, IOException {
        String receiver = email.getReceiver();
        String receivers[] = receiver.split(";");
        // "E://aim//"+studentEmail+"//"+fileName;;//本地路径
        // "//home//ubuntu//web//papersystem//"+studentEmail+"//"+fileName;//服务器路径
        String filepath = "//home//ubuntu//web//papersystem//"+studentEmail+"//"+fileName;
        MimeMessage mimMessage = getMailSender().createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimMessage, true, "utf-8");
            messageHelper.setFrom(account);
            messageHelper.setTo(receivers);
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(email.getContent(), true);
            messageHelper.addAttachment(MimeUtility.encodeWord(fileName),new File(filepath));
            getMailSender().send(mimMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
