package HRM.FinalProject.Repository;


import HRM.FinalProject.UserEntity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleRepository extends JpaRepository<Role, Long> {
    // Additional queries for admin-specific role management can be added here
}
