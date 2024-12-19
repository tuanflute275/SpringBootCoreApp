package com.core.app.JavaWebApp.Services.Impl;

import com.core.app.JavaWebApp.Services.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            // Tạo email MIME
            MimeMessage message = emailSender.createMimeMessage();

            // Tạo nội dung email từ Thymeleaf template
            String content = generateEmailContent(templateName, variables);

            // Thiết lập thông tin email
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail); // từ email
            helper.setTo(to); // email người nhận
            helper.setSubject(subject); // chủ đề
            helper.setText(content, true); // nội dung và định dạng HTML

            // Gửi email
            emailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace(); // xử lý lỗi
        }
    }

    private String generateEmailContent(String templateName, Map<String, Object> variables) {
        try {
            // Sử dụng Thymeleaf template để tạo nội dung email
            Context context = new Context();
            context.setVariables(variables);
            return templateEngine.process(templateName, context); // templateName là tên của mẫu email
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating email content"; // lỗi khi tạo nội dung
        }
    }
}
