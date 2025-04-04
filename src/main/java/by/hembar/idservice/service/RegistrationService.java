package by.hembar.idservice.service;

import by.hembar.idservice.model.CreateRoomRequest;
import by.hembar.idservice.model.DefaultResponse;
import by.hembar.idservice.model.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {

    DefaultResponse registrateUser(RegisterRequest request);
    DefaultResponse createUserRoom(CreateRoomRequest request);

}
