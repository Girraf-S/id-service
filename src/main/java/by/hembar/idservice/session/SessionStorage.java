package by.hembar.idservice.session;

import java.util.List;

public interface SessionStorage {
    SessionResponse createSession(String key, String username);

    SessionResponse extendSession(String key);

    SessionResponse deleteSession(String key);

    Session getSession(String key);

    List<Session> getAllSessions();

    SessionResponse sessionExpired(String key);
}