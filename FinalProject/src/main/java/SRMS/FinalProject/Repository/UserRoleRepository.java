package SRMS.FinalProject.Repository;



import SRMS.FinalProject.UserEntity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    // Custom queries if needed
}