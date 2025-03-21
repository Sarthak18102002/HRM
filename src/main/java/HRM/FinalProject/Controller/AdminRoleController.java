package HRM.FinalProject.Controller;

import HRM.FinalProject.DTO.ApiResponse;
import HRM.FinalProject.DTO.RoleAssignmentRequest;
import HRM.FinalProject.DTO.RoleUpdateRequest;
import HRM.FinalProject.Service.AdminRoleService;
import HRM.FinalProject.UserEntity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')") // Ensures only ADMIN can access
public class AdminRoleController {

    @Autowired
    private AdminRoleService adminRoleService;

    @PostMapping("/assign-role")
    public ResponseEntity<ApiResponse<String>> assignRoleToUser(@RequestBody RoleAssignmentRequest request) {
        try{


        adminRoleService.assignRoleToUser(request.getUserId(), request.getRoleId());
        return ResponseEntity.ok(new ApiResponse<>(200, "Role assigned successfully", null));
    }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, " " + e.getMessage(), null));
        }
    }

    @PostMapping("/remove-role")
    public ResponseEntity<ApiResponse<String>> removeRoleFromUser(@RequestBody RoleAssignmentRequest request) {
        try {
            adminRoleService.removeRoleFromUser(request.getUserId(), request.getRoleId());
            return ResponseEntity.ok(new ApiResponse<>(200, "Role removed successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, " "+ e.getMessage(), null));
        }
    }

    @PutMapping("/update-role")
    public ResponseEntity<ApiResponse<String>> updateUserRole(@RequestBody RoleUpdateRequest request) {
        try {
            adminRoleService.updateUserRole(request.getUserId(), request.getRoleId());
            return ResponseEntity.ok(new ApiResponse<>(200, "User role updated successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, " " + e.getMessage(), null));
        }
    }
}
