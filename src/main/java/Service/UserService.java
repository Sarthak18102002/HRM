package HRM.FinalProject.Service;

import HRM.FinalProject.DTO.UserDto;
import HRM.FinalProject.UserEntity.User;
import HRM.FinalProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAllUsers(String filterType) {
        List<User> users;

        switch (filterType) {
            case "verified":
                users = userRepository.findByEmailVerified(true);
                break;
            case "unverified":
                users = userRepository.findByEmailVerified(false);
                break;
            case "all":
            default:
                users = userRepository.findAll();
                break;
        }

        // Mapping users to UserDto list
        return users.stream().map(user -> new UserDto(
                        user.getId(),
                        user.getEmail(),
                        user.getUsername(),
                        user.getMobile_no(),
                        user.getEmailVerified(),
                        user.getAge(),
                        user.getBloodGroup(),
                        user.getEducation(),
                        user.getExperience(),
                        user.getTechnology()
                ))
                .collect(Collectors.toList());
    }
}
