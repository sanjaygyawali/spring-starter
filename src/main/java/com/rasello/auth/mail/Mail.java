package com.rasello.auth.mail;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String template;
    private Map<String, Object> props;

}
