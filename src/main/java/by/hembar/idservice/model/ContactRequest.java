package by.hembar.idservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {
    private Long id;
    private Long userRoomId;
    private String contactUserId;
    private String status;
}
