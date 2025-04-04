package by.hembar.idservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table
@Data
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

    public SecurityOptions(Long id, Date modify, String secretKey, Long sessionLifeTime, Long jwtLifeTime, Long userTimeInBlock, String adminKey, Boolean isActual) {
        this.id = id;
        this.modify = modify;
        this.secretKey = secretKey;
        this.sessionLifeTime = sessionLifeTime;
        this.jwtLifeTime = jwtLifeTime;
        this.userTimeInBlock = userTimeInBlock;
        this.adminKey = adminKey;
        this.isActual = isActual;
    }

    public SecurityOptions() {
    }

    public static SecurityOptionsBuilder builder() {
        return new SecurityOptionsBuilder();
    }

    public static class SecurityOptionsBuilder {
        private Long id;
        private Date modify;
        private String secretKey;
        private Long sessionLifeTime;
        private Long jwtLifeTime;
        private Long userTimeInBlock;
        private String adminKey;
        private Boolean isActual;

        SecurityOptionsBuilder() {
        }

        public SecurityOptionsBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SecurityOptionsBuilder modify(Date modify) {
            this.modify = modify;
            return this;
        }

        public SecurityOptionsBuilder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public SecurityOptionsBuilder sessionLifeTime(Long sessionLifeTime) {
            this.sessionLifeTime = sessionLifeTime;
            return this;
        }

        public SecurityOptionsBuilder jwtLifeTime(Long jwtLifeTime) {
            this.jwtLifeTime = jwtLifeTime;
            return this;
        }

        public SecurityOptionsBuilder userTimeInBlock(Long userTimeInBlock) {
            this.userTimeInBlock = userTimeInBlock;
            return this;
        }

        public SecurityOptionsBuilder adminKey(String adminKey) {
            this.adminKey = adminKey;
            return this;
        }

        public SecurityOptionsBuilder isActual(Boolean isActual) {
            this.isActual = isActual;
            return this;
        }

        public SecurityOptions build() {
            return new SecurityOptions(this.id, this.modify, this.secretKey, this.sessionLifeTime, this.jwtLifeTime, this.userTimeInBlock, this.adminKey, this.isActual);
        }

        public String toString() {
            return "SecurityOptions.SecurityOptionsBuilder(id=" + this.id + ", modify=" + this.modify + ", secretKey=" + this.secretKey + ", sessionLifeTime=" + this.sessionLifeTime + ", jwtLifeTime=" + this.jwtLifeTime + ", userTimeInBlock=" + this.userTimeInBlock + ", adminKey=" + this.adminKey + ", isActual=" + this.isActual + ")";
        }
    }
}
