package com.rasello.auth.mail;

import com.rasello.auth.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    private final EmailService emailService;

    public MailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ApiResponse<?> sendTestMail(){
        var email = new Mail();
        email.setFrom("lamsal.av@gmail.com");
        email.setTo("er.av91@gmail.com");
        email.setSubject("Welcome");
        email.setTemplate("welcome-template");
        var props = new HashMap<String, Object>();
        props.put("name", "Abhisek Lamsal");
        props.put("email", "er.av91@gmail.com");
        props.put("password", "N3pal@312!");
        props.put("link", "https://rasello.com");
        email.setProps(props);
        try {
            emailService.sendMail(email);
            return new ApiResponse<>(200, "Email sent");
        } catch (MessagingException e) {
            e.printStackTrace();
            return new ApiResponse<>(500, e.getMessage());
        }
    }
}
