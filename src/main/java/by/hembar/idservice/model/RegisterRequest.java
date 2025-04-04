package by.hembar.idservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull
    @Email
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9-_]{3,20}$|^$")
    private String login;
    @NotNull
    @Size(min = 4, max = 12)
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[1-9]).*")
    @EqualsAndHashCode.Exclude
    private String password;
    @NotNull
    @Size(min = 4, max = 12)
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[1-9]).*")
    @EqualsAndHashCode.Exclude
    private String repeatPassword;
    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = "(^[A-Z][a-z]+$)|(^[А-Я][а-я]+$)")
    private String firstName;
    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = "(^[A-Z][a-z]+$)|(^[А-Я][а-я]+$)")
    private String lastName;
}
