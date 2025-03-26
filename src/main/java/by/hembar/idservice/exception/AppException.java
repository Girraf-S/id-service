package by.hembar.idservice.exception;

import by.hembar.idservice.util.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AppException extends RuntimeException {
    private String message;
    private final HttpStatus httpStatus;
    private Message messageCode;

    public AppException(HttpStatus httpStatus, Message messageCode) {
        this.httpStatus = httpStatus;
        this.messageCode = messageCode;
    }

    public AppException(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
