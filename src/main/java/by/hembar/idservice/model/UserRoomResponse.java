package by.hembar.idservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoomResponse {
    private UserResponse user;
    private List<UserResponse> contactList;
    private Set<String> tags;
    private String description;
    private LocalDate birthDay;
    private LocalDateTime registrationDate;
}
