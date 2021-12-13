package com.rasello.auth.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final ITemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, ITemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMail(Mail mail) throws MessagingException {
        var mimeMessage = mailSender.createMimeMessage();
        var mimeHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        var context = new Context();
        context.setVariables(mail.getProps());
        var html = templateEngine.process(mail.getTemplate(), context);
        mimeHelper.setFrom(mail.getFrom());
        mimeHelper.setTo(mail.getTo());
        mimeHelper.setSubject(mail.getSubject());
        mimeHelper.setText(html, true);
        mailSender.send(mimeMessage);
    }
}
