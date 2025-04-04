package SRMS.FinalProject.Controller;

import SRMS.FinalProject.DTO.ApiResponse;
import SRMS.FinalProject.DTO.UserDto;
import SRMS.FinalProject.DTO.UserFilterRequest;
import SRMS.FinalProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/FetchData")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers(@RequestBody UserFilterRequest request) {
        if (request.getFilter() == null) {

            ApiResponse<List<UserDto>> response = new ApiResponse<>(400, "filter value is required", null);
            return ResponseEntity.badRequest().body(response);
        }

        int filter = request.getFilter();
        List<UserDto> users;
        String filterType;


        switch (filter) {
            case 0:
                filterType = "all";
                break;
            case 1:
                filterType = "verified";
                break;
            case 2:
                filterType = "unverified";
                break;
            default:
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>(400, " Invalid filter value.", null)
                );
        }

        users = userService.getAllUsers(filterType);
        return ResponseEntity.ok(new ApiResponse<>(200, "Data fetched successfully", users));
    }
}