package by.hembar.idservice.service;

import by.hembar.idservice.model.DefaultResponse;
import com.solbeg.nuserservice.entity.Role;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.LoginRequest;
import com.solbeg.nuserservice.model.RegisterRequest;
import com.solbeg.nuserservice.model.TokenResponse;
import com.solbeg.nuserservice.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final MailSenderService mailSenderService;

    @Transactional(readOnly = true)
    public DefaultResponse login(LoginRequest loginRequest) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();

        return jwtService.generateToken(userDetails.getUser());
    }

    @Transactional
    public void subscriberRegistration(RegisterRequest registerRequest) {
        registerUser(registerRequest, Role.SUBSCRIBER, true);
    }

    @Transactional
    public void journalistRegistration(RegisterRequest registerRequest) {
        User user = registerUser(registerRequest, Role.JOURNALIST, false);

        mailSenderService.sendUserInfoToAdmin(user);
    }

    private User registerUser(RegisterRequest registerRequest, Role role, boolean active) {
        checkRegisterData(registerRequest);

        User user = userMapper.registerRequestToUser(registerRequest, role, active);

        userService.save(user);

        return user;
    }

    private void checkRegisterData(RegisterRequest registerRequest) {
        if (userService.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email is exist");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
            throw new IllegalArgumentException("Password does not match");
        }
    }
}
