package by.hembar.idservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table
@Data
public class ContactList {

    @Id
    private Long id;

    //@Column(nullable = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserRoom ownerUser;

    @JoinColumn(name = "contact_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User contactUser;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedAt;
}
