package dance.joyfrom.rest.dancer;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.dancer.LessonProposal;
import dance.joyfrom.services.dancer.LessonProposals;
import dance.joyfrom.services.dancer.LessonProposalsService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
@RestController
public class DancerLessonProposalRestController {

    private final LessonProposalsService lessonProposalsService;

    public DancerLessonProposalRestController(LessonProposalsService lessonProposalsService) {
        this.lessonProposalsService = lessonProposalsService;
    }

    @RequestMapping(value = "/authorized/rest/dancer/lesson/proposal", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void addLessonProposal(@RequestBody LessonProposals lessonProposals, HttpServletResponse response) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail != null) {
            lessonProposalsService.add(lessonProposals, userEmail);
        }
    }

    @RequestMapping(value = "/authorized/rest/dancer/lesson/proposal", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<LessonProposal> getDancerLessonProposals(String timezone, HttpServletResponse response) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail == null) {
            return Collections.emptyList();
        } else {
            return lessonProposalsService.getTimezonedLessonProposals(userEmail, timezone);
        }
    }

}
