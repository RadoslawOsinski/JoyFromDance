package dance.joyfrom.rest.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by Radosław Osiński
 */
@RestController
public class LoginRestController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginRestController.class);
    private final AuthenticationManager authenticationManager;

    public LoginRestController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/loginz", method = RequestMethod.POST
//        ,
//        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        LOG.debug("Logging in for loginRequest: {}", loginRequest);
        LoginResponse loginResponse = new LoginResponse();
        try {
            Authentication authenticate = authenticationManager.authenticate(new JoyFromDanceAuthenticationToken(loginRequest.getEmail(), loginRequest.getRawPassword()));
//            loginResponse.setCookieValue(authenticate.get);
            LOG.info(authenticate.toString());
            loginResponse.setRoles(new ArrayList<>());
            loginResponse.setStatus(HttpStatus.OK.name());
        } catch (AuthenticationException e) {
            loginResponse.setStatus(HttpStatus.UNAUTHORIZED.name());
        }
        return loginResponse;
    }

}
