package by.hembar.idservice.service;

import by.hembar.idservice.entity.User;
import by.hembar.idservice.helper.Properties;
import by.hembar.idservice.model.DefaultResponse;
import by.hembar.idservice.model.Message;
import by.hembar.idservice.model.TokenResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtService {

    private final SecurityProperties defaultSecurityOptions;

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    @Value("${jwt.life.time}")
    private Long jwtLifeTime;

    @Autowired
    public JwtService(SecurityProperties defaultSecurityOptions) {
        this.defaultSecurityOptions = defaultSecurityOptions;
    }

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    public DefaultResponse generateToken(User user) {
        if(defaultSecurityOptions == null)
            return new DefaultResponse(Message.AUTH_NOT_ACCEPTED.name(), Message.TEMPORAL_NOT_ACCEPTABLE.name(), 500);

        if(!user.isActive())
            return new DefaultResponse(Message.USER_IS_BLOCKED.name(), null, 200);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtLifeTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getEmail());
        claims.put("id", user.getId());
        claims.put("iat", now);
        claims.put("exp", validity);
        if(user.isAdmin())
            claims.put("admin", Properties.get().ADMIN_KEY);


        String token = JWT.create()
                .withPayload(claims)
                .sign(Algorithm.HMAC256(jwtSecret));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new TokenResponse(token);
    }

    public Map<String, Object> extractClaims(String jwt){
        Map<String, Object> claims = new HashMap<>();
        DecodedJWT decodedJWT = decodeJWT(jwt);
        claims.put("id", decodedJWT.getClaim("id").asLong());
        claims.put("username", decodedJWT.getSubject());
        if(Properties.get().ADMIN_KEY.equals(decodedJWT.getClaim("admin").asString()))
            claims.put("isAdmin", true);

        return claims;
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = decodeJWT(token);
        return decodedJWT.getSubject();
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private DecodedJWT decodeJWT(String token) {
        JWTVerifier verifier = JWT
                .require(Algorithm.HMAC256(jwtSecret))
                .build();
        return Optional.ofNullable(verifier.verify(token))
                .orElseThrow(() -> new RuntimeException("Invalid token " + token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return decodeJWT(token).getExpiresAt();
    }
}
