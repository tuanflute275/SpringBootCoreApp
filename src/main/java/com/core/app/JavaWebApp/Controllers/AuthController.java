package com.core.app.JavaWebApp.Controllers;

import com.core.app.JavaWebApp.Exceptions.ResponseObject;
import com.core.app.JavaWebApp.Services.IAuthService;
import com.core.app.JavaWebApp.ViewModels.LoginViewModel;
import com.core.app.JavaWebApp.ViewModels.RegisterViewModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth Controller", description = "API for managing auths")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    IAuthService authService;

    @PostMapping("login")
    ResponseObject login(@RequestBody LoginViewModel model){
        return authService.login(model);
    }

    @PostMapping("register")
    ResponseObject register(@RequestBody RegisterViewModel model){
        return authService.register(model);
    }
}
