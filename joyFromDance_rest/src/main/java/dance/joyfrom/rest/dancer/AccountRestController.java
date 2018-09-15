package dance.joyfrom.rest.dancer;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.account.AccountService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialNotFoundException;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Radosław Osiński
 */
@RestController
public class AccountRestController {

    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/authorized/rest/dancer/account", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void updateBasicAccountInfo(@RequestParam String lastName, @RequestParam String firstName,
                                                      HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            accountService.updateBasicAccountInfo(userEmail, firstName, lastName);
            UserDispatcherService.updateFirstAndLastNameInCurrentSession(firstName, lastName);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

}

