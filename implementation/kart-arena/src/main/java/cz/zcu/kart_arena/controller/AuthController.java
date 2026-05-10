package cz.zcu.kart_arena.controller;

import cz.zcu.kart_arena.model.dto.LoginRequestDto;
import cz.zcu.kart_arena.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for authentication-related operations.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * AuthController class constructor.
     * @param authService - service for authentication-related operations.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticates a user and returns their role.
     * @param request - login request
     * @return - role of the user
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {
        try {
            String role = authService.login(request.username(), request.password());

            return ResponseEntity.ok(role);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


