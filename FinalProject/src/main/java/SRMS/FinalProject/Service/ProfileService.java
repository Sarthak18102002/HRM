package SRMS.FinalProject.Service;



import SRMS.FinalProject.DTO.ApiResponse;
import SRMS.FinalProject.DTO.ProfileDTO;
import SRMS.FinalProject.Repository.EducationRepository;
import SRMS.FinalProject.Repository.ExperienceRepository;
import SRMS.FinalProject.Repository.UserRepository;
import SRMS.FinalProject.UserEntity.Education;
import SRMS.FinalProject.UserEntity.Experience;
import SRMS.FinalProject.UserEntity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
@Service
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ExperienceRepository experienceRepository;


    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public ApiResponse<String> addProfileFields(Long id, ProfileDTO profileDTO) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with Id " + id));

            // user
            user.setTechnology(profileDTO.getTechnology());
            user.setBloodGroup(profileDTO.getBloodGroup());
            user.setFirstName(profileDTO.getFirstName());
            user.setMiddleName(profileDTO.getMiddleName());
            user.setLastName(profileDTO.getLastName());

            // Convert DOB format if not null
            if (profileDTO.getDOB() != null && !profileDTO.getDOB().isEmpty()) {
                LocalDate dob = LocalDate.parse(profileDTO.getDOB(), INPUT_FORMATTER);
                user.setDOB(dob.format(INPUT_FORMATTER)); // Store as dd-MM-yyyy
            }

            userRepository.save(user);

            // Education
            Education education = new Education();
            education.setUser(user);
            education.setCourse_name(profileDTO.getCourseName());
            education.setInstitute_name(profileDTO.getInstituteName());
            educationRepository.save(education);

            //Experience
            Experience experience = new Experience();
            experience.setUser(user);
            experience.setCompanyName(profileDTO.getCompanyName());
            experience.setMessage(profileDTO.getMessage());
            experience.setTechnology(profileDTO.getTechnology());

            // Convert StartDate and EndDate format if not null
            if (profileDTO.getStartDate() != null && !profileDTO.getStartDate().isEmpty()) {
                LocalDate startDate = LocalDate.parse(profileDTO.getStartDate(), INPUT_FORMATTER);
                experience.setStartDate(startDate.format(INPUT_FORMATTER)); // Store as dd-MM-yyyy
            }

            if (profileDTO.getEndDate() != null && !profileDTO.getEndDate().isEmpty()) {
                LocalDate endDate = LocalDate.parse(profileDTO.getEndDate(), INPUT_FORMATTER);
                experience.setEndDate(endDate.format(INPUT_FORMATTER)); // Store as dd-MM-yyyy
            }

            experienceRepository.save(experience);

            return new ApiResponse<>(200, "Profile fields stored successfully!", null);

        } catch (DataAccessException ex) {
            return new ApiResponse<>(500, "Database error occurred: " + ex.getMessage(), null);
        } catch (Exception ex) {
            return new ApiResponse<>(500, "An unexpected error occurred: " + ex.getMessage(), null);
        }
    }
}
