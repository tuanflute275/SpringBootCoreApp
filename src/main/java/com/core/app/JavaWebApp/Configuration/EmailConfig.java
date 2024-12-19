package com.core.app.JavaWebApp.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.smtp.auth}")
    private boolean smtpAuth;

    @Value("${spring.mail.smtp.starttls.enable}")
    private boolean startTls;

    @Value("${spring.mail.smtp.ssl.trust}")
    private String sslTrust;

    @Value("${spring.mail.protocol}")
    private String protocol;

    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", startTls);
        props.put("mail.smtp.ssl.trust", sslTrust);
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.connectiontimeout", 5000); // Optional, timeout value
        props.put("mail.smtp.timeout", 5000); // Optional, timeout value
        props.put("mail.smtp.writetimeout", 5000); // Optional, write timeout value
        props.put("mail.default-encoding", defaultEncoding);

        return mailSender;
    }
}
