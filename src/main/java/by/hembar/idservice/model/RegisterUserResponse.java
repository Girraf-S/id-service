package by.hembar.idservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponse extends DefaultResponse {
    private Long userId;

    public RegisterUserResponse(Long userId, int code) {
        super(code);
        this.userId = userId;
    }
}
