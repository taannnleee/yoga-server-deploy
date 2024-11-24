package org.example.yogabusinessmanagementweb.controller.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.yogabusinessmanagementweb.dto.request.user.LoginRequest;
import org.example.yogabusinessmanagementweb.dto.request.user.RegistrationRequest;
import org.example.yogabusinessmanagementweb.dto.request.user.ResetPasswordRequest;
import org.example.yogabusinessmanagementweb.dto.response.user.RegistrationResponse;
import org.example.yogabusinessmanagementweb.dto.response.ApiResponse;
import org.example.yogabusinessmanagementweb.dto.response.token.TokenRespone;
import org.example.yogabusinessmanagementweb.repositories.UserRepository;
import org.example.yogabusinessmanagementweb.service.Impl.AuthencationService;
import org.example.yogabusinessmanagementweb.service.Impl.WebSocketService;
import org.example.yogabusinessmanagementweb.service.UserService;
import org.example.yogabusinessmanagementweb.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {
    UserService userService;
    UserRepository userRepository;
    EmailService emailService;
    AuthencationService authencationService;
    WebSocketService webSocketService;

    @PostMapping("/register")
    public ApiResponse<?> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        userService.checkUserNotExist(registrationRequest);
        RegistrationResponse rp = userService.registerUser(registrationRequest);
        return new ApiResponse<>(HttpStatus.OK.value(), "User registered successfully", rp);
    }

    @PostMapping("verifyOTP_register")
    public ApiResponse<TokenRespone> verifyOTPRegister(@Valid @RequestParam String OTP, @RequestParam  String email) {
        authencationService.verifyOTP_register(OTP, email);
        return new ApiResponse<>(HttpStatus.OK.value(), "OTP is valid. Proceed with register.");

    }

    @PostMapping("/refresh")
    public ApiResponse<TokenRespone> refreshToken(HttpServletRequest request) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Refresh token success", authencationService.refresh(request));
    }
    @PostMapping("/login")
    public ApiResponse<TokenRespone> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            TokenRespone tokenRespone = authencationService.authentication(loginRequest);
            return new ApiResponse<>( HttpStatus.OK.value(),"Login success",tokenRespone);
        }catch (BadCredentialsException e){
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),"Bad credentials");
        }
    }

    @PostMapping("/login-admin")
    public ApiResponse<TokenRespone> loginAdmin(HttpServletRequest request,@Valid @RequestBody LoginRequest loginRequest) {
        try {
            TokenRespone tokenRespone = authencationService.authenticationAdmin(loginRequest);



            WebSocketSession session = (WebSocketSession) request.getAttribute("webSocketSession");
//            webSocketService.registerAdminSession(session);
            return new ApiResponse<>( HttpStatus.OK.value(),"Login success",tokenRespone);
        }catch (BadCredentialsException e){
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),"Bad credentials");
        }
    }

//    @PostMapping("/logout-admin")
//    public ApiResponse<?> logoutAdmin(HttpServletRequest request) {
//        WebSocketSession session = (WebSocketSession) request.getAttribute("webSocketSession");
//
//        if (session != null) {
//            webSocketService.unregisterAdminSession(session);  // Hủy đăng ký session admin
//            return new ApiResponse<>(HttpStatus.OK.value(), "Logout success");
//        } else {
//            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "No active WebSocket session found");
//        }
//    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Logout success", authencationService.logout(request));
    }

    @PostMapping("/forgot-password")
    public ApiResponse<?> forgotPassword(@Valid @RequestBody String email) {
        String result = authencationService.sendOTP(email);
        return new ApiResponse<>(HttpStatus.OK.value(), "Success"+result);
    }

    @PostMapping("/reset-password")
    public ApiResponse<?> resetPassword(@Valid @RequestBody String OTP, String email) {
        authencationService.resetPassword(OTP, email);
        return new ApiResponse<>(HttpStatus.OK.value(), "OTP is valid. Proceed with password reset.");

    }

    @PostMapping("/change-password")
    public ApiResponse<?> changePassword(@Valid @RequestBody ResetPasswordRequest request) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Success", authencationService.changePassword(request));
    }
}