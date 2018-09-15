package dance.joyfrom.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Radosław Osiński
 */
@RestController
public class LogoutRestController {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutRestController.class);
    private final SessionRegistry sessionRegistry;

    public LogoutRestController(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET},
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void logout(@CookieValue(name = "JSESSIONID", required = false) String sessionId,
                       @RequestParam(required = false, name = "JSESSIONID") String paramSessionId) {
        LOG.debug("Logout for sessionId: {}, paramSessionId: {}", sessionId, paramSessionId);
        if (sessionId != null) {
            sessionRegistry.removeSessionInformation(sessionId);
        }
        if (paramSessionId != null) {
            sessionRegistry.removeSessionInformation(paramSessionId);
        }
    }

}
