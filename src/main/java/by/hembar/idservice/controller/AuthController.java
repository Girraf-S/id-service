package by.hembar.idservice.controller;

import by.hembar.idservice.model.*;
import by.hembar.idservice.service.AuthService;
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
    public DefaultResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

//    @PostMapping("/registration/subscriber")
//    public ResponseEntity<?> subscriberRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
//        authService.subscriberRegistration(registerRequest);
//        return ResponseEntity.created(URI.create("/account")).build();
//    }
//
//    @PostMapping("/registration/journalist")
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public void journalistRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
//            authService.journalistRegistration(registerRequest);
//    }

    @PostMapping("/registration")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void journalistRegistration(@Valid @RequestBody RegisterRequest registerRequest) {
            authService.userRegistration(registerRequest);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
