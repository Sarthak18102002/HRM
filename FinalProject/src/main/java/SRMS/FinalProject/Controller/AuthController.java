package SRMS.FinalProject.Controller;

import SRMS.FinalProject.DTO.ApiResponse;
import SRMS.FinalProject.DTO.RegisterRequest;
import SRMS.FinalProject.Repository.UserRepository;
import SRMS.FinalProject.Service.AuthService;
import SRMS.FinalProject.Service.OtpService;
import SRMS.FinalProject.UserEntity.User;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthService authService;
//    private final OtpService otpService;
//    private final UserRepository userRepository;
//
//    public AuthController(AuthService authService, OtpService otpService, UserRepository userRepository) {
//        this.authService = authService;
//        this.otpService = otpService;
//        this.userRepository = userRepository;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody @Valid RegisterRequest request) {
//        ApiResponse<String> response = authService.registerUser(request, request.getPassword());
//
//        if (response.getStatus() == 200) {
//            otpService.sendOtp(request.getEmail());
//            return ResponseEntity.ok(response);
//        } else if (response.getStatus() == 400) {
//            return ResponseEntity.badRequest().body(response);
//        } else {
//            return ResponseEntity.status(500).body(response);
//        }
//    }
//
//    @PostMapping("/resend-otp")
//    public ResponseEntity<ApiResponse<String>> resendOtp(@RequestBody @Valid Map<String, String> request) {
//        String email = request.get("email");
//
//        try {
//            otpService.sendOtp(email);
//            return ResponseEntity.ok(new ApiResponse<>(200, "OTP sent successfully!", null));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Failed to send OTP: " + e.getMessage(), null));
//        }
//    }
//
//    @PostMapping("/verify-otp")
//    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestBody @Valid Map<String, String> request) {
//        String email = request.get("email");
//        String otp = request.get("otp");
//
//        if (otpService.verifyOtp(email, otp)) {
//            Optional<User> optionalUser = userRepository.findByEmail(email);
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//                user.setEmailVerified(true);
//                userRepository.save(user);
//                return ResponseEntity.ok(new ApiResponse<>(200, "Email verified successfully", null));
//            } else {
//                return ResponseEntity.badRequest().body(new ApiResponse<>(400, "User not found for this email!", null));
//            }
//        } else {
//            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Invalid or expired OTP!", null));
//        }
//    }
//}
//
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, OtpService otpService, UserRepository userRepository) {
        this.authService = authService;
        this.otpService = otpService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody @Valid RegisterRequest request) {
        ApiResponse<String> response = authService.registerUser(request, request.getPassword());

        if (response.getStatus() == 200) {
            try {
                otpService.sendOtp(request.getEmail()); // Sends OTP with email
                return ResponseEntity.ok(response);
            } catch (MessagingException e) {
                return ResponseEntity.status(500).body(new ApiResponse<>(500, "Failed to send OTP email: " + e.getMessage(), null));
            }
        } else if (response.getStatus() == 400) {
            return ResponseEntity.badRequest().body(response);
        } else {
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<String>> resendOtp(@RequestBody @Valid Map<String, String> request) {
        String email = request.get("email");

        try {
            otpService.sendOtp(email); // Resend OTP with email
            return ResponseEntity.ok(new ApiResponse<>(200, "OTP sent successfully!", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Failed to send OTP: " + e.getMessage(), null));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestBody @Valid Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        if (otpService.verifyOtp(email, otp)) {
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setEmailVerified(true);
                userRepository.save(user);
                return ResponseEntity.ok(new ApiResponse<>(200, "Email verified successfully", null));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, "User not found for this email!", null));
            }
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Invalid or expired OTP!", null));
        }
    }
}
