package by.hembar.idservice.service;

import by.hembar.idservice.entity.ActivationCode;
import by.hembar.idservice.entity.User;
import by.hembar.idservice.entity.UserRoom;
import by.hembar.idservice.helper.Properties;
import by.hembar.idservice.mapper.UserMapper;
import by.hembar.idservice.mapper.UserRoomMapper;
import by.hembar.idservice.model.CreateRoomRequest;
import by.hembar.idservice.model.DefaultResponse;
import by.hembar.idservice.model.RegisterRequest;
import by.hembar.idservice.model.RegisterUserResponse;
import by.hembar.idservice.repository.ActivationCodeRepository;
import by.hembar.idservice.repository.UserRepository;
import by.hembar.idservice.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final MailSenderService mailSenderService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserRoomMapper userRoomMapper;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;
    private final ActivationCodeRepository activationCodeRepository;

    @Value("${user.admin.activation-code.life-time:3600}")
    private int codeLifetime;

    @Transactional
    @Override
    public DefaultResponse registrateUser(RegisterRequest request) {
        User user = registerUser(request, true);

        UUID uuid = UUID.randomUUID();

        ActivationCode activationCode = ActivationCode.builder()
                .code(uuid.toString())
                .userId(user.getId())
                .expiredAt(LocalDateTime.now().plusSeconds(codeLifetime))
                .build();

        activationCodeRepository.save(activationCode);

        mailSenderService.verifyEmail(uuid.toString(), request.getEmail());//sendUserInfoToAdmin(user);

        return new RegisterUserResponse(user.getId(), HttpStatus.CREATED.value());
    }

    @Override
    public DefaultResponse createUserRoom(CreateRoomRequest request) {

        if (request.getUserId() == null || Properties.get().ADMIN_ID_LIST.contains(request.getUserId())) {
            return new DefaultResponse(HttpStatus.BAD_REQUEST.name(), "INVALID_USER_ID", HttpStatus.BAD_REQUEST.value());
        }

        if (userRoomRepository.findById(request.getUserId()).isPresent()) {
            return new DefaultResponse(HttpStatus.BAD_REQUEST.name(), "ROOM_WITH_USER_ID_EXIST", HttpStatus.BAD_REQUEST.value());
        }

        UserRoom userRoom = userRoomMapper.toUserRoom(request);

        userRoomRepository.save(userRoom);

        return new DefaultResponse(HttpStatus.CREATED.value());
    }


    private User registerUser(RegisterRequest registerRequest, boolean active) {

        if (registerRequest.getLogin() == null || registerRequest.getLogin().isEmpty())
            registerRequest.setLogin(registerRequest.getEmail());

        checkRegisterData(registerRequest);

        User user = userMapper.registerRequestToUser(registerRequest, active);

        userRepository.save(user);

        return user;
    }

    private void checkRegisterData(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email is exist");
        }
        if (userRepository.findByLogin(registerRequest.getLogin()).isPresent()) {
            throw new IllegalArgumentException("User with this login is exist");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
            throw new IllegalArgumentException("Password does not match");
        }
    }
}
