package dance.joyfrom.rest.admin.school;

import dance.joyfrom.services.admin.school.School;
import dance.joyfrom.services.admin.school.SchoolAdmin;
import dance.joyfrom.services.admin.school.SchoolAdminsService;
import dance.joyfrom.services.admin.school.SchoolService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Radosław Osiński
 */
@RestController
public class AdminSchoolRestController {

    private final SchoolService schoolService;
    private final SchoolAdminsService schoolAdminsService;

    public AdminSchoolRestController(SchoolService schoolService, SchoolAdminsService schoolAdminsService) {
        this.schoolService = schoolService;
        this.schoolAdminsService = schoolAdminsService;
    }

    @RequestMapping(value = "/authorized/rest/admin/school", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void putSchool(@RequestBody School school) {
        schoolService.add(school);
    }

    @RequestMapping(value = "/authorized/rest/admin/school", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<School> getSchool(@RequestParam Integer currentPage, @RequestParam Integer displayedRows,
                                            @RequestParam String name
    ) {
        return schoolService.get(currentPage, displayedRows, name);
    }

    @RequestMapping(value = "/authorized/rest/admin/school/schoolAdmin", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void addSchoolAdmin(@RequestParam Long schoolId, @RequestParam String newSchoolAdministrator) {
        schoolAdminsService.add(schoolId, newSchoolAdministrator);
    }

    @RequestMapping(value = "/authorized/rest/admin/school/schoolAdmin", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<SchoolAdmin> addSchoolAdmin(@RequestParam Long schoolId) {
        return schoolAdminsService.get(schoolId);
    }

    @RequestMapping(value = "/authorized/rest/admin/school/schoolAdmin", method = RequestMethod.DELETE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void deleteSchoolAdmin(@RequestParam Long schoolId, @RequestParam String email) {
        schoolAdminsService.delete(schoolId, email);
    }

}
