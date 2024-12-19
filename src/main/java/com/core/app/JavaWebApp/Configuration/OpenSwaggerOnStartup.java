package com.core.app.JavaWebApp.Configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OpenSwaggerOnStartup implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // Đường dẫn Swagger UI
        String swaggerUrl = "http://localhost:8080/swagger-ui/index.html";

        // Kiểm tra hệ điều hành
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // Đảm bảo Chrome đã được cài đặt ở đúng đường dẫn
                String chromePath = "C:/Program Files/Google/Chrome/Application/chrome.exe"; // Đường dẫn đến Chrome
                Runtime.getRuntime().exec(new String[]{chromePath, swaggerUrl});
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec(new String[]{"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome", swaggerUrl});
            } else if (os.contains("nix") || os.contains("nux")) {
                Runtime.getRuntime().exec(new String[]{"/usr/bin/google-chrome", swaggerUrl});
            }
            System.out.println("Trình duyệt Chrome đã được mở với Swagger UI.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Không thể mở Chrome: " + e.getMessage());
        }
    }
}
