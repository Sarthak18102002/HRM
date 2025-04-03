package SRMS.FinalProject.Repository;

import SRMS.FinalProject.UserEntity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByUserId(Long userId); // Fetch experiences of a specific user
}