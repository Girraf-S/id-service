package by.hembar.idservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecurityOptions {
    @Id
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modify;

    @Column(nullable = false)
    private String secretKey;

    @Column(nullable = false)
    private Long sessionLifeTime;

    @Column(nullable = false)
    private Long jwtLifeTime;

    @Column(nullable = false)
    private Long userTimeInBlock;

    @Column(nullable = false)
    private String adminKey;

    @Column(nullable = false)
    private Boolean isActual;
}
