package com.core.app.JavaWebApp.Services;

import com.core.app.JavaWebApp.Exceptions.ResponseObject;
import com.core.app.JavaWebApp.ViewModels.LoginViewModel;
import com.core.app.JavaWebApp.ViewModels.RegisterViewModel;

public interface IAuthService {
    ResponseObject register(RegisterViewModel model);
    ResponseObject login(LoginViewModel model);
}
