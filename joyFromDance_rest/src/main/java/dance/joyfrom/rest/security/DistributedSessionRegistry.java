package dance.joyfrom.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Radosław Osiński
 */
@Component
public class DistributedSessionRegistry implements SessionRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedSessionRegistry.class);

    private static final int LOCK_TIMEOUT = 500;
    private final IMap<String, Session> sessions;

    public DistributedSessionRegistry(HazelcastInstance hazelcastInstance) {
        this.sessions = hazelcastInstance.getMap("sessions");
    }

    @Override
    public List<Object> getAllPrincipals() {
        LOGGER.debug("Get all principals");
        return new ArrayList<>();
    }

    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        LOGGER.debug("Get all sessions for principal");
        return new ArrayList<>();
    }

    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        SessionInformation sessionInformation = null;
        try {
            if (sessions.tryLock(sessionId, LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                Session principal = sessions.get(sessionId);
//                sessionInformation = ...
            }
        } catch (InterruptedException e) {
            LOGGER.error("Thread interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            sessions.unlock(sessionId);
        }
        return sessionInformation;
    }

    @Override
    public void refreshLastRequest(String sessionId) {
        LOGGER.info("refreshLastRequest");
        try {
            if (sessions.tryLock(sessionId, LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                Session session = sessions.get(sessionId);
                sessions.put(sessionId, new Session(session.getUserId(), session.getLoginTime(), Instant.now()));
//                sessions.put(sessionId, new Session(session.getUserId(), session.getLoginTime(), Instant.now()), environment.getProperty("hazelcast.client.sessions.cache.ttl", Long.class, 10L), TimeUnit.MINUTES);
            }
        } catch (InterruptedException e) {
            LOGGER.error("Thread interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            sessions.unlock(sessionId);
        }
    }

    @Override
    public void registerNewSession(String sessionId, Object principal) {
        LOGGER.debug("Register new session for sessionId: {}", sessionId);
        try {
            if (sessions.tryLock(sessionId, LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
//                sessions.put(sessionId, new Session(principal.getUserId(), session.getLoginTime(), Instant.now()));
//                sessions.put(sessionId, principal, environment.getProperty("hazelcast.client.sessions.cache.ttl", Long.class, 10L), TimeUnit.MINUTES);
            }
        } catch (InterruptedException e) {
            LOGGER.error("Thread interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            sessions.unlock(sessionId);
        }
    }

    @Override
    public void removeSessionInformation(String sessionId) {
        LOGGER.debug("Remove session information for sessionId: {}", sessionId);
        try {
            if (sessions.tryLock(sessionId, LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                sessions.remove(sessionId);
            }
        } catch (InterruptedException e) {
            LOGGER.error("Thread interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            sessions.unlock(sessionId);
        }
    }
}
