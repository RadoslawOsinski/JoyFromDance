package dance.joyfrom.rest.school;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.school.RoomLesson;
import dance.joyfrom.services.school.RoomLessonService;
import dance.joyfrom.services.school.TeachersForLesson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
@RestController
public class SchoolRoomTimetableRestController {

    private final RoomLessonService roomLessonService;

    public SchoolRoomTimetableRestController(RoomLessonService roomLessonService) {
        this.roomLessonService = roomLessonService;
    }

    @RequestMapping(value = "/authorized/rest/school/room/timetable", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void addRoomLesson(@RequestBody RoomLesson roomLesson, HttpServletResponse response) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail != null) {
            if (roomLessonService.isAuthorizedToAddLesson(userEmail, roomLesson.getRoomId())) {
                roomLessonService.add(roomLesson);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    @RequestMapping(value = "/authorized/rest/school/room/timetable/{id}", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void editRoomLesson(@PathVariable Long id, @RequestBody RoomLesson roomLesson, HttpServletResponse response) {
        roomLesson.setId(id);
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail != null) {
            if (roomLessonService.isAuthorizedToAddLesson(userEmail, roomLesson.getRoomId())) {
                roomLessonService.update(roomLesson);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }

    @RequestMapping(value = "/authorized/rest/school/room/timetable/{id}/teachers", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void assignTeachersToLesson(@PathVariable Long id, @RequestBody TeachersForLesson teachersForLesson, HttpServletResponse response) {
//        String userEmail = null;
//        try {
//            userEmail = UserDispatcherService.getCurrentUserEmail();
//        } catch (CredentialNotFoundException e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//        if (userEmail != null) {
//            if (roomLessonService.isAuthorizedToAddLesson(userEmail, id)) {
        //todo check permissions!
                roomLessonService.assignTeachersToLesson(id, teachersForLesson.getEmails());
//            } else {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            }
//        }
    }

    @RequestMapping(value = "/authorized/rest/school/room/timetable", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<RoomLesson> getRoomLessons(Long roomId, Long start, Long end, String timezone) {
        return roomLessonService.getTimezonedRoomLessonsForSchool(roomId, start, end, timezone);
    }
}
