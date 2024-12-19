package com.core.app.JavaWebApp.Services;

import java.util.Map;

public interface IEmailService {
    void sendEmail(String to, String subject, String templateName, Map<String, Object> variables);
}
