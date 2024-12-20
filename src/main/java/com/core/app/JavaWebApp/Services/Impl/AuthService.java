package com.core.app.JavaWebApp.Services.Impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.core.app.JavaWebApp.Configuration.JwtTokenProvider;
import com.core.app.JavaWebApp.Entities.User;
import com.core.app.JavaWebApp.Exceptions.ResponseObject;
import com.core.app.JavaWebApp.Repositories.UserRepository;
import com.core.app.JavaWebApp.Services.IAuthService;
import com.core.app.JavaWebApp.Services.IEmailService;
import com.core.app.JavaWebApp.Utils.Constant;
import com.core.app.JavaWebApp.ViewModels.LoginViewModel;
import com.core.app.JavaWebApp.ViewModels.RegisterViewModel;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService implements IAuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    IEmailService emailService;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Override
    @Transactional
    public ResponseObject register(RegisterViewModel model) {
        // validate username
        if (model.getUsername() == null || model.getUsername().isEmpty()) {
            return new ResponseObject(Constant.FAILED_STATUS, "Username is required", null);
        }
        if (model.getUsername().length() < 3 || model.getUsername().length() > 20) {
            return new ResponseObject(Constant.FAILED_STATUS, "Username must be between 3 and 20 characters", null);
        }
        if (!model.getUsername().matches("^[a-zA-Z0-9_]+$")) {
            return new ResponseObject(Constant.FAILED_STATUS, "Username can only contain letters, numbers, and underscores", null);
        }

        // Validate fullName
        if (model.getFullName() == null || model.getFullName().isEmpty()) {
            return new ResponseObject(Constant.FAILED_STATUS, "FullName is required", null);
        }
        if (model.getFullName().length() < 5 || model.getFullName().length() > 50) {
            return new ResponseObject(Constant.FAILED_STATUS, "FullName must be between 5 and 50 characters", null);
        }
        if (!model.getFullName().matches("^[a-zA-Z ]+$")) {
            return new ResponseObject(Constant.FAILED_STATUS, "FullName can only contain letters and spaces", null);
        }

        // Validate email
        if (model.getEmail() == null || model.getEmail().isEmpty()) {
            return new ResponseObject(Constant.FAILED_STATUS, "Email is required", null);
        }
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = emailPattern.matcher(model.getEmail());
        if (!matcher.matches()) {
            return new ResponseObject(Constant.FAILED_STATUS, "Invalid email format", null);
        }

        // Validate password
        if (model.getPassword() == null || model.getPassword().isEmpty()) {
            return new ResponseObject(Constant.FAILED_STATUS, "Password is required", null);
        }
        if (model.getPassword().length() < 6) {
            return new ResponseObject(Constant.FAILED_STATUS, "Password must be at least 6 characters", null);
        }
        if (!model.getPassword().matches(".*[a-zA-Z].*") || !model.getPassword().matches(".*[0-9].*")) {
            return new ResponseObject(Constant.FAILED_STATUS, "Password must contain at least one letter and one number", null);
        }
        if (model.getPassword().matches(".*[^a-zA-Z0-9].*")) {
            return new ResponseObject(Constant.FAILED_STATUS, "Password cannot contain special characters", null);
        }
        // ==========================================================
        try {
            // Kiểm tra nếu email hoặc username đã tồn tại
            if (userRepository.existsByEmail(model.getEmail())) {
                logger.warn("Email {} already exists", model.getEmail());
                return new ResponseObject(
                        Constant.FAILED_STATUS,
                        "Email already exists",
                        null
                );
            }

            if (userRepository.existsByUsername(model.getUsername())) {
                logger.warn("Username {} already exists", model.getUsername());
                return new ResponseObject(
                        Constant.FAILED_STATUS,
                        "Username already exists",
                        null
                );
            }

            // Tạo đối tượng User từ model
            User user = new User();
            user.setUserName(model.getUsername());
            user.setEmail(model.getEmail());
            user.setFullName(model.getFullName());

            // Mã hóa mật khẩu sử dụng BCrypt
            String hashedPassword = BCrypt.withDefaults().hashToString(12, model.getPassword().toCharArray());
            user.setPassword(hashedPassword);

            // Gán các thông tin mặc định khác
            user.setActive(1); // Kích hoạt tài khoản
            user.setRole(0); // Gán quyền mặc định là "User"
            user.setCreateDate(new Date());
            user.setCreateBy(Constant.SYSTEM);

            // Lưu user vào cơ sở dữ liệu
            userRepository.save(user);
            logger.info("User {} registered successfully", model.getUsername());

            // Gửi email xác nhận đăng ký thành công
            Map<String, Object> variables = new HashMap<>();
            variables.put("fullName", user.getFullName());
            emailService.sendEmail(user.getEmail(), "Welcome to Our Service!", "RegisterSuccessMail", variables);
            logger.info("Registration success email sent to {}", user.getEmail());

            return new ResponseObject(
                    Constant.SUCCESS_STATUS,
                    "User registered successfully",
                    null
            );
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error - data integrity violation: {}", e.getMessage());
            return new ResponseObject(
                    Constant.FAILED_STATUS,
                    "Database error - data integrity violation",
                    e.getMessage()
            );
        } catch (Exception e) {
            logger.error("Error during user registration: {}", e.getMessage());
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }

    @Override
    @Transactional
    public ResponseObject login(LoginViewModel model) {
        // validate username
        if (model.getUsername() == null || model.getUsername().isEmpty()) {
            return new ResponseObject(Constant.FAILED_STATUS, "Username is required", null);
        }
        if (model.getUsername().length() < 3 || model.getUsername().length() > 20) {
            return new ResponseObject(Constant.FAILED_STATUS, "Username must be between 3 and 20 characters", null);
        }
        if (!model.getUsername().matches("^[a-zA-Z0-9_]+$")) {
            return new ResponseObject(Constant.FAILED_STATUS, "Username can only contain letters, numbers, and underscores", null);
        }
        // Validate password
        if (model.getPassword() == null || model.getPassword().isEmpty()) {
            return new ResponseObject(Constant.FAILED_STATUS, "Password is required", null);
        }
        if (model.getPassword().length() < 6) {
            return new ResponseObject(Constant.FAILED_STATUS, "Password must be at least 6 characters", null);
        }
        if (!model.getPassword().matches(".*[a-zA-Z].*") || !model.getPassword().matches(".*[0-9].*")) {
            return new ResponseObject(Constant.FAILED_STATUS, "Password must contain at least one letter and one number", null);
        }
        if (model.getPassword().matches(".*[^a-zA-Z0-9].*")) {
            return new ResponseObject(Constant.FAILED_STATUS, "Password cannot contain special characters", null);
        }

        try {
            // Tìm người dùng trong cơ sở dữ liệu theo username (hoặc email)
            Optional<User> userOpt = userRepository.findByUsername(model.getUsername());
            if (!userOpt.isPresent()) {
                return new ResponseObject(Constant.FAILED_STATUS, "User not found", null);
            }

            User user = userOpt.get();

            // Xác thực mật khẩu: So sánh mật khẩu người dùng nhập vào với mật khẩu đã mã hóa
            BCrypt.Result result = BCrypt.verifyer().verify(model.getPassword().toCharArray(), user.getPassword());
            if (!result.verified) {
                return new ResponseObject(Constant.FAILED_STATUS, "Invalid password", null);
            }

            // Nếu mật khẩu đúng, tạo một token JWT (nếu sử dụng JWT)
            String token = jwtTokenProvider.createToken(model.getUsername());
            // Trả về thông tin đăng nhập thành công và token
            return new ResponseObject(Constant.SUCCESS_STATUS, "Login successful", token);
        } catch (Exception e) {
            return new ResponseObject(Constant.ERROR_STATUS, "Error during login", e.getMessage());
        }
    }
}
