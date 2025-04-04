package by.hembar.idservice.controller;

import by.hembar.idservice.model.CreateRoomRequest;
import by.hembar.idservice.model.RegisterRequest;
import by.hembar.idservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/user")
    public ResponseEntity<?> regUser(RegisterRequest registerRequest)    {
        return registrationService.registrateUser(registerRequest);
    }

    @PostMapping("/createRoom")
    public ResponseEntity<?> createRoom(CreateRoomRequest createRoomRequest)    {
        return registrationService.createUserRoom(createRoomRequest);
    }
}
