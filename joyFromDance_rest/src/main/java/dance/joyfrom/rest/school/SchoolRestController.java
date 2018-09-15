package dance.joyfrom.rest.school;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.paging.TablePageableResponse;
import dance.joyfrom.services.admin.school.School;
import dance.joyfrom.services.admin.school.SchoolAdmin;
import dance.joyfrom.services.admin.school.SchoolAdminsService;
import dance.joyfrom.services.admin.school.SchoolService;
import dance.joyfrom.services.school.SchoolTeacher;
import dance.joyfrom.services.school.SchoolTeachersService;
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
public class SchoolRestController {

    private final SchoolTeachersService schoolTeachersService;
    private final SchoolAdminsService schoolAdminsService;
    private final SchoolService schoolService;

    public SchoolRestController(SchoolTeachersService schoolTeachersService, SchoolAdminsService schoolAdminsService, SchoolService schoolService) {
        this.schoolTeachersService = schoolTeachersService;
        this.schoolAdminsService = schoolAdminsService;
        this.schoolService = schoolService;
    }

    @RequestMapping(value = "/authorized/rest/school", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<School> getSchools(@RequestParam Integer currentPage, @RequestParam Integer displayedRows,
                                   @RequestParam String name
    ) {
        return schoolService.get(currentPage, displayedRows, name);
    }

    @RequestMapping(value = "/authorized/rest/school/teachers", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public TablePageableResponse<SchoolTeacher> getSchoolTeachers(@RequestParam Integer currentPage, @RequestParam Integer displayedRows,
                                                                  @RequestParam String firstNameSearch,
                                                                  @RequestParam String lastNameSearch,
                                                                  @RequestParam String emailSearch,
                                                                  @RequestParam(required = false) Boolean ascendingEmailSortOrder,
                                                                  HttpServletResponse response
    ) {
        if (ascendingEmailSortOrder == null) {
            ascendingEmailSortOrder = Boolean.TRUE;
        }
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        TablePageableResponse<SchoolTeacher> tablePageableResponse = new TablePageableResponse<>();
        if (userEmail == null) {
            tablePageableResponse.setResults(Collections.emptyList());
            tablePageableResponse.setTotalNumberOfPages(0L);
        } else {
            List<SchoolAdmin> adminSchools = schoolAdminsService.getAdminSchools(userEmail);
            //todo admin should have more than one school
            SchoolAdmin school = adminSchools.get(0);
            tablePageableResponse = schoolTeachersService.getSchoolTeachersResponse(
                currentPage, displayedRows, Long.valueOf(school.getId()), firstNameSearch, lastNameSearch, emailSearch,
                ascendingEmailSortOrder
            );
        }
        return tablePageableResponse;
    }

    @RequestMapping(value = "/authorized/rest/school/teacher", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void addSchoolTeacher(@RequestParam String teacherEmail, HttpServletResponse response
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
            schoolTeachersService.add(Long.valueOf(school.getId()), teacherEmail);
        }
    }

}
