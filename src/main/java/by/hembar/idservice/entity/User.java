package by.hembar.idservice.entity;

import by.hembar.idservice.helper.Properties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
@SQLRestriction("is_active = true")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String login;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public boolean isAdmin() {
        return Properties.get().ADMIN_ID_LIST.contains(id);
    }
}
