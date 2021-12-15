package com.rasello.auth.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Value("${mail.host}")
    protected String host;

    @Value("${mail.port}")
    protected Integer port;

    @Value("${mail.username}")
    protected String username;

    @Value("${mail.password}")
    protected String password;

    @Value("${mail.debug}")
    protected Boolean debug;

    @Value("${mail.smtp.auth}")
    protected Boolean smtpAuth;

    @Value("${mail.smtp.starttls}")
    protected String startTls;

    @Value("${mail.transport.protocol}")
    protected String protocol;

    @Bean
    public JavaMailSender mailSender() {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        var props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", startTls);
        props.put("mail.debug", debug);
        return mailSender;
    }
}
