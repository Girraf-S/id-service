package by.hembar.idservice.filter;

import by.hembar.idservice.exception.AppException;
import by.hembar.idservice.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Value("${jwt.bearer}")
    private String bearer;
    @Value("${jwt.begin-index}")
    private int beginIndex;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String username;

        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!authHeader.startsWith(bearer)) {
            throw new AppException("Header should be started with 'Bearer'", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        jwt = authHeader.substring(beginIndex);
        username = jwtService.extractUsername(jwt);
        Objects.requireNonNull(username);
        setAuthenticationIfTokenValid(username, jwt);
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationIfTokenValid(String username, String jwt) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Claims claims = Jwts.claims().add(jwtService.extractClaims(jwt))
                .add("jwt", jwt)
                .build();
        if (jwtService.isTokenValid(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(),
                    null,
                    userDetails.getAuthorities()
            );
            authToken.setDetails(
                    claims
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

}
