package by.hembar.idservice.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateRoomRequest {
    private Long userId;
    private String description;
    private String tags;
    private LocalDate birthDay;
}
