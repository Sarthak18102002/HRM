package HRM.FinalProject.Controller;


import HRM.FinalProject.DTO.ApiResponse;
import HRM.FinalProject.DTO.RegisterRequest;
import HRM.FinalProject.Repository.UserRepository;
import HRM.FinalProject.Service.AuthService;
import HRM.FinalProject.Service.OtpService;
import HRM.FinalProject.UserEntity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

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
            otpService.sendOtp(request.getEmail());
            return ResponseEntity.ok(response);
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
            otpService.sendOtp(email);
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


