package by.hembar.idservice.model;

//import com.solbeg.nuserservice.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserArchiveResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private boolean isActive;

}
