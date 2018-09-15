package dance.joyfrom.rest.school;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.admin.school.SchoolAdmin;
import dance.joyfrom.services.admin.school.SchoolAdminsService;
import dance.joyfrom.services.school.LessonType;
import dance.joyfrom.services.school.SchoolLessonTypesService;
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
public class SchoolLessonTypeRestController {

    private final SchoolLessonTypesService schoolLessonTypesService;
    private final SchoolAdminsService schoolAdminsService;

    public SchoolLessonTypeRestController(
        SchoolLessonTypesService schoolLessonTypesService,
        SchoolAdminsService schoolAdminsService
    ) {
        this.schoolLessonTypesService = schoolLessonTypesService;
        this.schoolAdminsService = schoolAdminsService;
    }

    @RequestMapping(value = "/authorized/rest/school/lesson/type", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<LessonType> getLessonTypesForSchoolAdmin(@RequestParam Integer currentPage, @RequestParam Integer displayedRows,
                                                         @RequestParam String lessonTypeNameSearch, HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail != null) {
            List<SchoolAdmin> adminSchools = schoolAdminsService.getAdminSchools(userEmail);
            //todo admin should have more than one school
            SchoolAdmin school = adminSchools.get(0);
            return schoolLessonTypesService.get(currentPage, displayedRows, Long.valueOf(school.getId()), lessonTypeNameSearch);
        }
        return Collections.emptyList();
    }

    @RequestMapping(value = "/authorized/rest/school/{schoolId}/lesson/type", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<LessonType> getLessonTypesForDancer(@PathVariable Long schoolId) {
        return schoolLessonTypesService.get(schoolId);
    }

    @RequestMapping(value = "/authorized/rest/school/lesson/type", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void addSchoolRoom(@RequestParam String lessonTypeName, @RequestParam String lessonTypeDescription, HttpServletResponse response
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
            schoolLessonTypesService.add(Long.valueOf(school.getId()), lessonTypeName, lessonTypeDescription);
        }
    }
}
