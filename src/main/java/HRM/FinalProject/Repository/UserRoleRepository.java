package HRM.FinalProject.Repository;

import HRM.FinalProject.UserEntity.Role;
import HRM.FinalProject.UserEntity.User;
import HRM.FinalProject.UserEntity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUser(User user);  // Fetch the existing role of the user
    Optional<UserRole> findByUserAndRole(User user, Role role);
    boolean existsByUserAndRole(User user, Role role);
}


