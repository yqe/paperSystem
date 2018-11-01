package com.nju.paperSystem.config;


import com.nju.paperSystem.entity.email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

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
        System.out.println(host);
        System.out.println(account);
        System.out.println(password);
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", isAuth);
        properties.put("mail.smtp.ssl.enable", Boolean.valueOf(ssl));
        properties.put("mail.smtp.timeout", outTime);
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    public void sendSimpleMail(email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(account);
        String receiver = email.getReceiver();
        String receivers[] = receiver.split(";");
        simpleMailMessage.setTo(receivers);
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getContent());
        getMailSender().send(simpleMailMessage);
    }

}
