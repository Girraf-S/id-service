package by.hembar.idservice.exception;

import by.hembar.idservice.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ExceptionHandlingFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AppException e) {
            sendError(response, e.getMessage(), e.getHttpStatus());
        } catch (Exception e) {
            sendError(response, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendError(HttpServletResponse response, String message, HttpStatus httpStatus) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(message);
        String jsonErrorResponse = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonErrorResponse);
    }
}
