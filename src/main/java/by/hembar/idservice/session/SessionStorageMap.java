package by.hembar.idservice.session;

import by.hembar.idservice.helper.Properties;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class SessionStorageMap implements SessionStorage {

    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();//key - jwt

    @Override
    public SessionResponse createSession(String key, String username) {
        if (sessionMap.containsKey(key)) {
            return SessionResponse.SESSION_EXIST;
        }
        Session session = Session.builder()
                .sessionId(UUID.randomUUID().toString())
                .startSession(LocalDateTime.now())
                .lastEvent(LocalDateTime.now())
                .endSession(LocalDateTime.now().plusSeconds(Properties.get().SESSION_LIFE_TIME / 1000))
                .username(username)
                .extendedTimes(0)
                .build();

        sessionMap.put(key, session);
        return SessionResponse.SESSION_CREATED;
    }

    @Transactional
    @Override
    public SessionResponse extendSession(String key) {
        if (!sessionMap.containsKey(key)) {
            return SessionResponse.SESSION_NOT_EXIST;
        }
        Session session = sessionMap.get(key);
        if (session.getExtendedTimes() >= Properties.get().MAX_EXTENDED_SESSION_TIMES) {
            return SessionResponse.MAX_TIMES_EXTENDED_OVER;
        }
        sessionMap.compute(key, (k, s) -> {
                    s.setEndSession(LocalDateTime.now().plusSeconds(Properties.get().SESSION_LIFE_TIME / 1000));
                    s.setExtendedTimes(session.getExtendedTimes() + 1);
                    return s;
                }
        );
        return SessionResponse.SESSION_EXTENDED;
    }

    @Override
    public SessionResponse deleteSession(String key) {
        if (!sessionMap.containsKey(key)) {
            return SessionResponse.SESSION_NOT_EXIST;
        }
        sessionMap.remove(key);
        return SessionResponse.SESSION_DELETED;
    }

    @Override
    public Session getSession(String key) {
        return sessionMap.get(key);
    }

    @Override
    public List<Session> getAllSessions() {
        return sessionMap.entrySet().stream().map(e -> new Session(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    @Override
    public SessionResponse sessionExpired(String key) {
        if (!sessionMap.containsKey(key)) {
            return SessionResponse.SESSION_NOT_EXIST;
        }
        return this.sessionMap.get(key).getEndSession().isBefore(LocalDateTime.now()) ? SessionResponse.OK : SessionResponse.SESSION_EXPIRED;
    }


}
