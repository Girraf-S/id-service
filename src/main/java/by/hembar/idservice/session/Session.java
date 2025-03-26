package by.hembar.idservice.session;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private String sessionId;//uuid
    private int extendedTimes;
    private String jwt;//key in sessionMap
    private String username;
    private LocalDateTime startSession;
    private LocalDateTime lastEvent;
    private LocalDateTime endSession;

    public Session(String jwt, Session session){
        this.jwt = jwt;
        this.username = session.getUsername();
        this.startSession = LocalDateTime.now();
        this.lastEvent = LocalDateTime.now();
        this.endSession = LocalDateTime.now();
        this.extendedTimes = session.getExtendedTimes();
        this.sessionId = session.getSessionId();
    }
}
