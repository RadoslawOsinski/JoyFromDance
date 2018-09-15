package dance.joyfrom.rest.school;

import dance.joyfrom.rest.i18n.I18nValidationService;
import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.admin.school.SchoolAccount;
import dance.joyfrom.services.admin.school.SchoolAccountService;
import dance.joyfrom.services.paging.TablePageableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by Radosław Osiński
 */
@RestController
public class SchoolAccountsRestController {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolAccountsRestController.class);

    private final SchoolAccountService schoolAccountService;

    public SchoolAccountsRestController(SchoolAccountService schoolAccountService) {
        this.schoolAccountService = schoolAccountService;
    }

    @RequestMapping(value = "/authorized/rest/school/schoolAccounts", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public TablePageableResponse<SchoolAccount> getSchoolAccounts(@RequestParam Integer currentPage, @RequestParam Integer displayedRows,
                                                                  @RequestParam String schoolNameSearch, HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        TablePageableResponse<SchoolAccount> tablePageableResponse = new TablePageableResponse<>();
        if (userEmail == null) {
            tablePageableResponse.setResults(Collections.emptyList());
            tablePageableResponse.setTotalNumberOfPages(0L);
        } else {
            tablePageableResponse = schoolAccountService.get(currentPage, displayedRows, userEmail, schoolNameSearch);
        }
        return tablePageableResponse;
    }

    @RequestMapping(value = "/authorized/rest/school/schoolAccounts", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void addSchool(@RequestParam String schoolName, HttpServletResponse response, Locale locale) {
        if (schoolName == null || schoolName.trim().isEmpty() || schoolName.trim().length() > 200) {
            String msg = I18nValidationService.i18n("SchoolNameMustContainFrom1To200Characters", locale);
            LOG.info("Cannot create school. " + msg);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            String userEmail = null;
            try {
                userEmail = UserDispatcherService.getCurrentUserEmail();
            } catch (CredentialNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            if (userEmail != null) {
                schoolAccountService.add(schoolName, userEmail);
            }
        }
    }

    @RequestMapping(value = "/authorized/rest/school/schoolAccounts/{schoolId}", method = RequestMethod.DELETE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void removeSchool(@PathVariable Long schoolId, HttpServletResponse response) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail != null) {
            schoolAccountService.remove(schoolId, userEmail);
        }
    }

}
