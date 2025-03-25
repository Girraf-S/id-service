package by.hembar.idservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AppException extends RuntimeException{
    private String message;
    private final HttpStatus httpStatus;
}
