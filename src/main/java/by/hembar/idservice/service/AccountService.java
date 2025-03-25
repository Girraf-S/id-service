package by.hembar.idservice.service;

import com.solbeg.nuserservice.entity.ActivationCode;
import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.exception.AppException;
import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.UserResponse;
import com.solbeg.nuserservice.repository.ActivationCodeRepository;
import com.solbeg.nuserservice.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ActivationCodeRepository activationCodeRepository;
    private final MailSenderService mailSenderService;

    @Transactional(readOnly = true)
    public UserResponse getCurrentProfileInfo() {
        Long id = getIdFromSecurityContext();
        User user = userService.findById(id).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND)
        );
        return userMapper.userToUserResponse(user);
    }

    @Transactional
    public void sendActivationCode() {
        Long id = getIdFromSecurityContext();
        User user = userService.findById(id).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND)
        );

        ActivationCode activationCode = ActivationCode.builder()
                .userId(id)
                .code(
                        String.valueOf(UUID.randomUUID())
                )
                .expiredAt(LocalDateTime.now().plusMinutes(5L))
                .build();
        activationCodeRepository.save(activationCode);

        mailSenderService.verifyEmail(activationCode.getCode(), user.getEmail());
    }

    @Transactional
    public void verifyMail(String code) {
        ActivationCode activationCode = activationCodeRepository.findByCode(code).orElseThrow(
                () -> new AppException("Activation code don't exist or expired", HttpStatus.BAD_REQUEST)
        );
        if (activationCode.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new AppException("Activation code expired", HttpStatus.BAD_REQUEST);

        User user = userService.findById(activationCode.getUserId()).orElseThrow(
                () -> new AppException("User not found", HttpStatus.NOT_FOUND)
        );
        user.setEmailVerified(true);
        userService.save(user);
    }

    private Long getIdFromSecurityContext() {
        return Long.parseLong(AuthUtil.extractClaimStringValue(
                SecurityContextHolder.getContext().getAuthentication(), "id")
        );
    }
}
