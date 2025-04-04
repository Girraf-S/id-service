package by.hembar.idservice.model;

import by.hembar.idservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {

    private Long id;
    private Long userRoomId;
    private User contactUser;
    private String status;
    private Date addedAt;
}
