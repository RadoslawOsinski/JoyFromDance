package dance.joyfrom.rest.dancer;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.dancer.DancerSchoolsService;
import dance.joyfrom.services.school.RoomLesson;
import dance.joyfrom.services.school.RoomLessonService;
import dance.joyfrom.services.school.SchoolRoom;
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
public class DancerSchoolRoomsRestController {

    private final DancerSchoolsService dancerSchoolsService;
    private final RoomLessonService roomLessonService;

    public DancerSchoolRoomsRestController(DancerSchoolsService dancerSchoolsService, RoomLessonService roomLessonService) {
        this.dancerSchoolsService = dancerSchoolsService;
        this.roomLessonService = roomLessonService;
    }

    @RequestMapping(value = "/authorized/rest/dancer/school/room", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<SchoolRoom> getSchoolRooms(@RequestParam Long schoolId) {
        return dancerSchoolsService.getSchoolRooms(schoolId);
    }

    @RequestMapping(value = "/authorized/rest/dancer/school/room/timetable", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<RoomLesson> getRoomLessons(
        Long schoolRoomId, Long start, Long end, String timezone, HttpServletResponse response
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
            return roomLessonService.getTimezonedRoomLessonsForDancer(schoolRoomId, start, end, timezone, userEmail);
        }
    }

    @RequestMapping(value = "/authorized/rest/dancer/school/room/timetable/{lessonId}", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public RoomLesson getRoomLesson(
        @PathVariable Long lessonId, @RequestParam String timezone, HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        } else {
            return roomLessonService.getRoomLessonForDancer(lessonId, timezone, userEmail);
        }
    }

}
