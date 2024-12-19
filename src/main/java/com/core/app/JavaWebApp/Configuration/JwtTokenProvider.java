package com.core.app.JavaWebApp.Configuration;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
//Lớp này sẽ chịu trách nhiệm tạo token và xác thực token.
public class JwtTokenProvider {

    // Key bí mật dùng để mã hóa và giải mã JWT (nên lưu trữ trong biến môi trường hoặc file cấu hình bảo mật)
    private String secretKey = "your-secret-key";

    // Thời gian sống của token (1 ngày)
    private long validityInMilliseconds = 3600000L; // 1 hour

    // Tạo JWT token từ user
    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)  // Lưu tên người dùng vào phần Subject của token
                .setIssuedAt(now)      // Thời gian tạo token
                .setExpiration(validity) // Thời gian hết hạn của token
                .signWith(SignatureAlgorithm.HS256, secretKey) // Mã hóa token bằng HMAC với key bí mật
                .compact();
    }

    // Xác thực token
    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // Lấy thông tin người dùng từ token
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Kiểm tra xem token có hợp lệ không
    public boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // Kiểm tra và xác thực token
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}