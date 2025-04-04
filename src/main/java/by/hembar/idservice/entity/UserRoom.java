package by.hembar.idservice.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table
public class UserRoom {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column
    private String description;

    @Column
    private String tags;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate birthDay;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime registrationDate;

    @Transient
    private Set<String> tagSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerUser")
    private List<ContactList> contactList;




    @PostConstruct
    private void init(){
        tagSet = Arrays.stream(tags.split(",")).collect(Collectors.toSet());
    }


}
