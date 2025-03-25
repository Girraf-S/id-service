package by.hembar.idservice.controller;

import com.solbeg.nuserservice.model.*;
import com.solbeg.nuserservice.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/registration/subscriber")
    public ResponseEntity<?> subscriberRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.subscriberRegistration(registerRequest);
        return ResponseEntity.created(URI.create("/account")).build();
    }

    @PostMapping("/registration/journalist")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void journalistRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
            authService.journalistRegistration(registerRequest);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
