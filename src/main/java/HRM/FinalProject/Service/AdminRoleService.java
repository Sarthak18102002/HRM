package HRM.FinalProject.Service;


import HRM.FinalProject.Repository.AdminRoleRepository;
import HRM.FinalProject.Repository.AdminUserRepository;
import HRM.FinalProject.Repository.UserRoleRepository;
import HRM.FinalProject.UserEntity.Role;
import HRM.FinalProject.UserEntity.User;
import HRM.FinalProject.UserEntity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminRoleService {

    @Autowired
    private AdminRoleRepository adminRoleRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public void assignRoleToUser(Long userId, Long roleId) {
        try {
            User user = adminUserRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));

            Role role = adminRoleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role with ID " + roleId + " not found"));

            if (userRoleRepository.existsByUserAndRole(user, role)) {
                throw new RuntimeException("User already has this role assigned");
            }

            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);

            userRoleRepository.save(userRole);
        } catch (Exception e) {
            throw new RuntimeException("Error assigning role: " + e.getMessage(), e);
        }
    }

    public void removeRoleFromUser(Long userId, Long roleId) {
        try {
            User user = adminUserRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));

            Role role = adminRoleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role with ID " + roleId + " not found"));

            UserRole userRole = userRoleRepository.findByUserAndRole(user, role)
                    .orElseThrow(() -> new RuntimeException("User does not have the specified role"));

            userRoleRepository.delete(userRole);
        } catch (Exception e) {
            throw new RuntimeException("Error removing role: " + e.getMessage(), e);
        }
    }

    public void updateUserRole(Long userId, Long roleId) {
        try {
            User user = adminUserRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));

            Role newRole = adminRoleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("New role with ID " + roleId + " not found"));

            // Find the current role assigned to the user
            Optional<UserRole> currentUserRole = userRoleRepository.findByUser(user);

            if (currentUserRole.isPresent()) {
                // Remove the old role
                userRoleRepository.delete(currentUserRole.get());
            }

            // Assign the new role
            UserRole newUserRole = new UserRole();
            newUserRole.setUser(user);
            newUserRole.setRole(newRole);

            userRoleRepository.save(newUserRole);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user role: " + e.getMessage(), e);
        }
    }
}
