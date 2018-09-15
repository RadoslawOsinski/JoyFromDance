package dance.joyfrom.rest.dancer;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.dancer.DancerSchool;
import dance.joyfrom.services.dancer.DancerSchoolsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
@RestController
public class DancerSchoolsRestController {

    private final DancerSchoolsService dancerSchoolsService;

    public DancerSchoolsRestController(DancerSchoolsService dancerSchoolsService) {
        this.dancerSchoolsService = dancerSchoolsService;
    }

    @RequestMapping(value = "/authorized/rest/dancer/school", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<DancerSchool> getDancerSchools(@RequestParam Integer currentPage, @RequestParam Integer displayedRows,
                                               @RequestParam String searchSchoolName,
                                               HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail == null) {
            return Collections.emptyList();
        } else {
            return dancerSchoolsService.get(
                currentPage, displayedRows, searchSchoolName, userEmail
            );
        }
    }

    @RequestMapping(value = "/authorized/rest/dancer/school", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void addDancerSchool(@RequestParam String schoolId, HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail != null) {
            dancerSchoolsService.add(Long.valueOf(schoolId), userEmail);
        }
    }

}

