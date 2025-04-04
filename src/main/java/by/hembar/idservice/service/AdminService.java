package by.hembar.idservice.service;

import by.hembar.idservice.exception.AppException;
import by.hembar.idservice.model.UserArchiveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;

    public Page<UserArchiveResponse> getAllUsers(Boolean isActive, Pageable pageable) {
        return userService.findAllByActive(isActive, pageable);
    }

    public void activateUserById(Long id) {
        userService.findNonActiveById(id).ifPresentOrElse(
                user -> userService.activateUserById(id),
                () -> {
                    throw new AppException("Not found user with id = " + id, HttpStatus.NOT_FOUND);
                }
        );
    }
}
