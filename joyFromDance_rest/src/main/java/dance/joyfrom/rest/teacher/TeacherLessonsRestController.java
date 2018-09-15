package dance.joyfrom.rest.teacher;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.school.RoomLessonForTeacher;
import dance.joyfrom.services.teacher.TeacherLessonsService;
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
public class TeacherLessonsRestController {

    private final TeacherLessonsService teacherLessonsService;

    public TeacherLessonsRestController(TeacherLessonsService teacherLessonsService) {
        this.teacherLessonsService = teacherLessonsService;
    }

    @RequestMapping(value = "/authorized/rest/teacher/lessons", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<RoomLessonForTeacher> getTeacherLessons(@RequestParam Integer currentPage, @RequestParam Integer displayedRows,
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
            return teacherLessonsService.get(
                currentPage, displayedRows, searchSchoolName, searchRoom, searchDescription, timezone, userEmail
            );
        }
    }

}
