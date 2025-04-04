package by.hembar.idservice.service;

import by.hembar.idservice.entity.User;
import by.hembar.idservice.mapper.UserMapper;
import by.hembar.idservice.model.VerifyEmailRequest;
import by.hembar.idservice.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${host.id-service}")
    private String userDomain;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.admin-mail}")
    private String adminMail;

    public void verifyEmail(String code, String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String subject = "Activation code";
        String message = "Hello! To verify your email visit link '"
                + userDomain + "account/verify-mail/" + code +
                "'";
        mailMessage.setFrom(username);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    public void sendUserInfoToAdmin(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String subject = "Activate user with id";

        String message = "User: " +
                user.toString() +
                "\n" +
                "Activate user: link '" +
                userDomain +
                "/admin/activate/" +
                user.getId() +
                "'";

        mailMessage.setFrom(user.getEmail());
        mailMessage.setTo(adminMail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

//    @Value("${service.mail-service}")
//    private String mailService;
//    @Value("${jwt.bearer}")
//    private String bearer;
//
//    private final RestTemplate restTemplate;
//    private final UserMapper userMapper;
//
//    public void verifyEmail(String code, String email) {
//        HttpHeaders headers = new HttpHeaders();
//        String jwt = AuthUtil.extractClaimStringValue(SecurityContextHolder.getContext().getAuthentication(), "jwt");
//        headers.set(HttpHeaders.AUTHORIZATION, bearer + jwt);
//
//        HttpEntity<VerifyEmailRequest> entity = new HttpEntity<>(
//                VerifyEmailRequest.builder()
//                        .email(email)
//                        .activationCode(code)
//                        .build(),
//                headers
//        );
//
//        restTemplate.exchange(mailService + "mail/verify", HttpMethod.POST,
//                entity, Void.class).getBody();
//    }
//
//    public void sendUserInfoToAdmin(User user) {
//        restTemplate.exchange(mailService + "mail/activate-link", HttpMethod.POST,
//                new HttpEntity<>(userMapper.userToUserResponse(user)), Void.class).getBody();
//    }
}
