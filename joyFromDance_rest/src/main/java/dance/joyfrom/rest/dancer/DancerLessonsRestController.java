package dance.joyfrom.rest.dancer;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.dancer.DancerLessonsService;
import dance.joyfrom.services.school.RoomLessonForDancer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
@RestController
public class DancerLessonsRestController {

    private final DancerLessonsService dancerLessonsService;

    public DancerLessonsRestController(DancerLessonsService dancerLessonsService) {
        this.dancerLessonsService = dancerLessonsService;
    }

    @RequestMapping(value = "/authorized/rest/dancer/lessons", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<RoomLessonForDancer> getDancerLessons(@RequestParam Integer currentPage, @RequestParam Integer displayedRows,
                                                      @RequestParam String searchSchoolName,
                                                      @RequestParam String searchRoom,
                                                      @RequestParam String searchDescription,
                                                      @RequestParam String timezone,
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
            return dancerLessonsService.get(
                currentPage, displayedRows, searchSchoolName, searchRoom, searchDescription, timezone, userEmail
            );
        }
    }

    @RequestMapping(value = "/authorized/rest/dancer/lessons", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void assignLessonToDancer(@RequestParam Long lessonId, @RequestParam String dancerEmail,
                                                      HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail == null || !userEmail.equals(dancerEmail)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            dancerLessonsService.assignLessonToDancer(lessonId, userEmail);
        }
    }

    @RequestMapping(value = "/authorized/rest/dancer/lesson/{lessonId}/pair", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void assignPairByDancer(
        @PathVariable Long lessonId,
        @RequestParam String dancerEmail, @RequestParam String otherDancerEmail,
        HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail != null && userEmail.equals(dancerEmail)) {
            dancerLessonsService.assignPairByDancer(lessonId, dancerEmail, otherDancerEmail);
        }
    }

    @RequestMapping(value = "/authorized/rest/dancer/lesson/pair/{pairId}/acceptance", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void acceptAssignedPairByDancer(
        @PathVariable Long pairId,
        HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail != null) {
            dancerLessonsService.acceptAssignedPairByDancer(pairId, userEmail);
        }
    }

}

