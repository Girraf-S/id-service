package by.hembar.idservice.service;

//import com.solbeg.nuserservice.entity.ActivationCode;
//import com.solbeg.nuserservice.entity.User;
//import com.solbeg.nuserservice.exception.AppException;
//import com.solbeg.nuserservice.mapper.UserMapper;
//import com.solbeg.nuserservice.model.UserResponse;
//import com.solbeg.nuserservice.repository.ActivationCodeRepository;
//import com.solbeg.nuserservice.util.AuthUtil;

import by.hembar.idservice.entity.ActivationCode;
import by.hembar.idservice.entity.ContactList;
import by.hembar.idservice.entity.User;
import by.hembar.idservice.entity.UserRoom;
import by.hembar.idservice.exception.AppException;
import by.hembar.idservice.mapper.ContactListMapper;
import by.hembar.idservice.mapper.UserMapper;
import by.hembar.idservice.model.DefaultResponse;
import by.hembar.idservice.model.UserResponse;
import by.hembar.idservice.model.UserRoomResponse;
import by.hembar.idservice.repository.ActivationCodeRepository;
import by.hembar.idservice.repository.UserRepository;
import by.hembar.idservice.repository.UserRoomRepository;
import by.hembar.idservice.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final MailSenderService mailSenderService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ContactListMapper contactListMapper;
    private final ActivationCodeRepository activationCodeRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;
    //private final MailSenderService mailSenderService;

    @Value("${user.admin.activation-code.life-time:3600}")
    private int codeLifetime;

    public UserRoomResponse getUserRoom() {
        Long userId = getIdFromSecurityContext();
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new AppException(HttpStatus.NOT_FOUND);
        }

        UserRoom userRoom = user.getUserRoom();

        return UserRoomResponse.builder()
                .tags(userRoom.getTagSet())
                .birthDay(userRoom.getBirthDay())
                .registrationDate(userRoom.getRegistrationDate())
                .contactList(userRoom.getContactList().stream()
                        .map(cl -> userMapper.userToUserResponse(cl.getContactUser())).toList())
                .user(userMapper.userToUserResponse(user))
                .description(userRoom.getDescription())
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentProfileInfo() {
        Long id = getIdFromSecurityContext();
        User user = userService.findById(id).orElseThrow(
                () -> new AppException(HttpStatus.NOT_FOUND)
        );
        return userMapper.userToUserResponse(user);
    }

    @Transactional
    public DefaultResponse sendActivationCode() {
        try {
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

            //mailSenderService.verifyEmail(activationCode.getCode(), user.getEmail());
            return new DefaultResponse(HttpStatus.OK.value());
        } catch (Exception e) {
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), "SEND_ACTIVATION_CODE_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Transactional
    public DefaultResponse verifyMail(String code) {
        try {

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
            return new DefaultResponse(HttpStatus.OK.value());
        } catch (Exception e) {
            return new DefaultResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), "MAIL_VERIFICATION_FAILED", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Transactional
    public DefaultResponse askVerifyCode(){

        Long userId = getIdFromSecurityContext();
        User user = userRepository.findById(userId).orElse(null);
        if (user.isEmailVerified()){
            return new DefaultResponse(HttpStatus.OK.name(), "EMAIL_IS_VERIFIED", HttpStatus.OK.value());
        }
        UUID uuid = UUID.randomUUID();

        ActivationCode activationCode = ActivationCode.builder()
                .code(uuid.toString())
                .userId(user.getId())
                .expiredAt(LocalDateTime.now().plusSeconds(codeLifetime))
                .build();

        activationCodeRepository.save(activationCode);

        mailSenderService.verifyEmail(uuid.toString(), user.getEmail());//sendUserInfoToAdmin(user);
        return new DefaultResponse(HttpStatus.OK.name(), "CHECK_MAIL_FOR_VERIFICATION", HttpStatus.OK.value());
    }

    private Long getIdFromSecurityContext() {
        return Long.parseLong(AuthUtil.extractClaimStringValue(
                SecurityContextHolder.getContext().getAuthentication(), "id")
        );
    }
}
