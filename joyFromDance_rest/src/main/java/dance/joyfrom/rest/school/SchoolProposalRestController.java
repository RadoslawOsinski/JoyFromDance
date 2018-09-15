package dance.joyfrom.rest.school;

import dance.joyfrom.rest.session.UserDispatcherService;
import dance.joyfrom.services.admin.school.SchoolAdmin;
import dance.joyfrom.services.admin.school.SchoolAdminsService;
import dance.joyfrom.services.dancer.AggregatedLessonProposal;
import dance.joyfrom.services.dancer.LessonProposalsService;
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
public class SchoolProposalRestController {

    private final LessonProposalsService lessonProposalsService;
    private final SchoolAdminsService schoolAdminsService;

    public SchoolProposalRestController(LessonProposalsService lessonProposalsService, SchoolAdminsService schoolAdminsService) {
        this.lessonProposalsService = lessonProposalsService;
        this.schoolAdminsService = schoolAdminsService;
    }

    @RequestMapping(value = "/authorized/rest/school/{schoolId}/lesson/proposal", method = RequestMethod.GET,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<AggregatedLessonProposal> getDancerLessonProposals(@PathVariable Long schoolId, @RequestParam String timezone,
                                                                   @RequestParam String lessonTypeName,
                                                                   @RequestParam Integer minDancers,
                                                                   HttpServletResponse response
    ) {
        String userEmail = null;
        try {
            userEmail = UserDispatcherService.getCurrentUserEmail();
        } catch (CredentialNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        if (userEmail == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.emptyList();
        } else {
            List<SchoolAdmin> adminSchools = schoolAdminsService.getAdminSchools(userEmail);
            //todo admin should have more than one school
            SchoolAdmin school = adminSchools.get(0);
//            if (schoolId == schoolId) {
                return lessonProposalsService.getTimezonedSchoolLessonProposals(
                    Long.valueOf(school.getId()), lessonTypeName, minDancers, timezone
                );
//            } else {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return Collections.emptyList();
//            }
        }
    }

}
