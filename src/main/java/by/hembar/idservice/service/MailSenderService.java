package by.hembar.idservice.service;

import com.solbeg.nuserservice.entity.User;
import com.solbeg.nuserservice.mapper.UserMapper;
import com.solbeg.nuserservice.model.VerifyEmailRequest;
import com.solbeg.nuserservice.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MailSenderService {
    @Value("${service.mail-service}")
    private String mailService;
    @Value("${jwt.bearer}")
    private String bearer;

    private final RestTemplate restTemplate;
    private final UserMapper userMapper;

    public void verifyEmail(String code, String email) {
        HttpHeaders headers = new HttpHeaders();
        String jwt = AuthUtil.extractClaimStringValue(SecurityContextHolder.getContext().getAuthentication(), "jwt");
        headers.set(HttpHeaders.AUTHORIZATION, bearer + jwt);

        HttpEntity<VerifyEmailRequest> entity = new HttpEntity<>(
                VerifyEmailRequest.builder()
                        .email(email)
                        .activationCode(code)
                        .build(),
                headers
        );

        restTemplate.exchange(mailService + "mail/verify", HttpMethod.POST,
                entity, Void.class).getBody();
    }

    public void sendUserInfoToAdmin(User user) {
        restTemplate.exchange(mailService + "mail/activate-link", HttpMethod.POST,
                new HttpEntity<>(userMapper.userToUserResponse(user)), Void.class).getBody();
    }
}
