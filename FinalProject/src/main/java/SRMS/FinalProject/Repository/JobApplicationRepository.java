package SRMS.FinalProject.Repository;



import SRMS.FinalProject.UserEntity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
}
