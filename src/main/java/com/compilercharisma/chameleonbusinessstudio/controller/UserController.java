package com.compilercharisma.chameleonbusinessstudio.controller;

import com.compilercharisma.chameleonbusinessstudio.dto.UserResponse;
import com.compilercharisma.chameleonbusinessstudio.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path="/api/v2/users")
@Slf4j
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * This endpoint fetches all users from Vendia Share
     *
     * @return {@link UserResponse}
     */
    @GetMapping("/getAll")
    public Mono<UserResponse> fetchAllUsersFromVendia() {
       log.info("Fetching all users from Vendia Share...");
       var response = userService.getAllUsers();
       log.info("Finished fetching all users from Vendia!");
       return response;
    }

    @PostMapping("/admin")
    public @ResponseBody String createAdmin(@RequestParam(name="email") String email){
        boolean success = true;
        try {
            userService.createAdmin(email);
        } catch(Exception ex){
            ex.printStackTrace();
            success = false;
        }
        
        return (success) 
                ? String.format("created %s as an admin", email)
                : String.format("failed to create %s as an admin", email);
    }
}
