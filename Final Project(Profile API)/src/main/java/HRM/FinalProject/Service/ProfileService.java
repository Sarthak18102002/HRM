package HRM.FinalProject.Service;

import HRM.FinalProject.DTO.ApiResponse;
import HRM.FinalProject.UserEntity.User;
import HRM.FinalProject.DTO.ProfileDTO;
import HRM.FinalProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    public ApiResponse<String> addProfileFields(Long id, ProfileDTO profileDTO) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                user.setTechnology(profileDTO.getTechnology());
                user.setAge(profileDTO.getAge());
                user.setBloodGroup(profileDTO.getBloodGroup());
                user.setExperience(profileDTO.getExperience());
                user.setEducation(profileDTO.getEducation());

                userRepository.save(user);

                return new ApiResponse<>(200, "Profile fields stored successfully!", null);
            } else {
                return new ApiResponse<>(400, "User not found with Id " + id, null);
            }

        } catch (DataAccessException ex) {
            return new ApiResponse<>(500, "Database error occurred: " + ex.getMessage(), null);
        } catch (Exception ex) {
            return new ApiResponse<>(500, "An unexpected error occurred: " + ex.getMessage(), null);
        }
    }
}

