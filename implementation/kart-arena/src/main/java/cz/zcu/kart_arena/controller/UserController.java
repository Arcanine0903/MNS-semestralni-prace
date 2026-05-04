package cz.zcu.kart_arena.controller;

import cz.zcu.kart_arena.model.User;
import cz.zcu.kart_arena.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for User-related operations.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Registers a new user.
     * @param username - chosen username
     * @param email - chosen email
     * @param password - chosen password
     * @return ResponseEntity with status code 200 if successful, 400 if username or email already exists
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username,
                                           @RequestParam String email,
                                           @RequestParam String password) {
        try {
            User savedUser = userService.registerUser(username, email, password);
            return ResponseEntity.ok("Uživatel " + savedUser.getUsername() + " byl úspěšně zaregistrován!");
        } catch (IllegalArgumentException e) {
            // If duplicity occurs, return 400 Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}