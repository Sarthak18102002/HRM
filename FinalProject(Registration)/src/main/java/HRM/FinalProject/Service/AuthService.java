package HRM.FinalProject.Service;
import HRM.FinalProject.DTO.ApiResponse;
import HRM.FinalProject.DTO.RegisterRequest;
import HRM.FinalProject.Repository.RoleRepository;
import HRM.FinalProject.Repository.UserRepository;
import HRM.FinalProject.Repository.UserRoleRepository;
import HRM.FinalProject.UserEntity.Role;
import HRM.FinalProject.UserEntity.User;
import HRM.FinalProject.UserEntity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository; // Add this line to inject RoleRepository
//
//    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    @Transactional
//    public ApiResponse<String> registerUser(RegisterRequest request, String password) {
//        try {
//            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//                return new ApiResponse<>(400, "Email is already registered!", null);
//            }
//
//            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
//                return new ApiResponse<>(400, "Username is already taken!", null);
//            }
//
//            if (userRepository.findBymobileNo(String.valueOf(request.getMobile_no())).isPresent()) {
//                return new ApiResponse<>(400, "Mobile Number is already registered!", null);
//            }
//
//            // Fetch USER role from Role table
//            Role userRole = roleRepository.findByRoleName("User").orElseThrow(() -> new RuntimeException("Default USER role not found. Please insert it in Role table."));
//
//            User user = new User();
//            user.setEmail(request.getEmail());
//            user.setUsername(request.getUsername());
//            user.setPassword(passwordEncoder.encode(password));
//            user.setMobileNo(String.valueOf(request.getMobile_no()));
//            user.setEmailVerified(false);
//            user.setRole(userRole); // ✅ Assign USER role
//
//            userRepository.save(user);
//
//            return new ApiResponse<>(200, "User registered successfully. OTP sent to email.", null);
//        } catch (DataAccessException ex) {
//            System.err.println("Database error occurred: " + ex.getMessage());
//            return new ApiResponse<>(500, "Registration failed due to database error. Please try again later.", null);
//        } catch (Exception ex) {
//            System.err.println("An unexpected error occurred: " + ex.getMessage());
//            return new ApiResponse<>(500, "An unexpected error occurred during registration.", null);
//        }
//    }
//}


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository; // Add this line to inject UserRoleRepository

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public ApiResponse<String> registerUser(RegisterRequest request, String password) {
        try {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return new ApiResponse<>(400, "Email is already registered!", null);
            }

            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                return new ApiResponse<>(400, "Username is already taken!", null);
            }

            if (userRepository.findBymobileNo(String.valueOf(request.getMobile_no())).isPresent()) {
                return new ApiResponse<>(400, "Mobile Number is already registered!", null);
            }

            // Fetch USER role from Role table
            Role userRole = roleRepository.findByRoleName("USER")
                    .orElseThrow(() -> new RuntimeException("Default USER role not found. Please insert it in Role table."));

            // Create new user
            User user = new User();
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(password));
            user.setMobileNo(String.valueOf(request.getMobile_no()));
            user.setEmailVerified(false);
//            user.setRole(userRole);


            userRepository.save(user);

            // It Combines Userid and RoleId
            UserRole userRoleEntry = new UserRole();
            userRoleEntry.setUser(user);
            userRoleEntry.setRole(userRole);
            userRoleRepository.save(userRoleEntry);

            return new ApiResponse<>(200, "User registered successfully. OTP sent to email.", null);
        } catch (DataAccessException ex) {
            System.err.println("Database error occurred: " + ex.getMessage());
            return new ApiResponse<>(500, "Registration failed due to database error. Please try again later.", null);
        } catch (Exception ex) {
            System.err.println("An unexpected error occurred: " + ex.getMessage());
            return new ApiResponse<>(500, "An unexpected error occurred during registration.", null);
        }
    }
}
