package com.core.app.JavaWebApp.Configuration;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Value("${spring.api.prefix}")
    private String apiPrefix;

    private static final String[] PUBLIC_URLS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/api/category",
            "/api/auth/login",
            "/api/auth/register",
            "/api/actuator",
            "/api/actuator/health"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors();
        http
                .csrf().disable()  // Tắt CSRF nếu không sử dụng (thường dùng cho RESTful APIs)
                .authorizeRequests()
                .requestMatchers(PUBLIC_URLS).permitAll()  // Các endpoint không yêu cầu xác thực
                .anyRequest().authenticated()  // Các yêu cầu khác yêu cầu xác thực
                .and()
                .formLogin()
                .loginPage("/api/v1/auth/login")  // Chỉ định trang đăng nhập tùy chỉnh
                .permitAll()  // Cho phép mọi người truy cập trang đăng nhập mà không cần xác thực
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    // Trả về mã lỗi 401 và thông báo lỗi dạng JSON khi không có token hoặc token không hợp lệ
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Set mã lỗi 401
                    response.setContentType("application/json");  // Set kiểu trả về là JSON
                    response.getWriter().write("{\"status\": 401, \"message\": \"Unauthorized: Token is missing or invalid\"}");
                })
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)  // Thêm bộ lọc JWT
                .logout()
                .permitAll();  // Cho phép logout từ mọi trang
        return http.build();
    }
}
