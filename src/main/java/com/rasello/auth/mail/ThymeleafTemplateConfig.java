package com.rasello.auth.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.charset.StandardCharsets;

@Configuration
public class ThymeleafTemplateConfig {
    /*@Bean
    public ITemplateEngine springTemplateEngine(){
        var engine = new SpringTemplateEngine();
        engine.addTemplateResolver(htmlTemplateResolver());
        return engine;
    }
*/
    /*@Bean
    public ITemplateResolver htmlTemplateResolver() {
        var templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("email-templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return templateResolver;
    }*/
}
