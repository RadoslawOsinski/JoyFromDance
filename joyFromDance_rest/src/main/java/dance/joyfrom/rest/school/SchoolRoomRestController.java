package dance.joyfrom.rest.school;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.admin.school.SchoolAdmin;
import dance.joyfrom.services.admin.school.SchoolAdminsService;
import dance.joyfrom.services.school.SchoolRoom;
import dance.joyfrom.services.school.SchoolRoomService;
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
public class SchoolRoomRestController {

    private final SchoolAdminsService schoolAdminsService;
    private final SchoolRoomService schoolRoomService;

    public SchoolRoomRestController(SchoolAdminsService schoolAdminsService, SchoolRoomService schoolRoomService) {
        this.schoolAdminsService = schoolAdminsService;
        this.schoolRoomService = schoolRoomService;
    }

    @RequestMapping(value = "/authorized/rest/school/room", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<SchoolRoom> getSchoolRooms(@RequestParam Integer currentPage, @RequestParam Integer displayedRows,
                                           @RequestParam String roomNameSearch, HttpServletResponse response
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
            List<SchoolAdmin> adminSchools = schoolAdminsService.getAdminSchools(userEmail);
            //todo admin should have more than one school
            SchoolAdmin school = adminSchools.get(0);
            return schoolRoomService.get(currentPage, displayedRows, Long.valueOf(school.getId()), roomNameSearch);
        }
    }

    @RequestMapping(value = "/authorized/rest/school/room", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void addSchoolRoom(@RequestParam String roomName, HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail != null) {
            List<SchoolAdmin> adminSchools = schoolAdminsService.getAdminSchools(userEmail);
            //        //todo admin should have more than one school
            SchoolAdmin school = adminSchools.get(0);
            schoolRoomService.add(Long.valueOf(school.getId()), roomName);
        }
    }
}
