//package dance.joyfrom.rest.admin.jobs;
//
//import dance.joyfrom.jobs.stats.JobStat;
//import dance.joyfrom.jobs.stats.JobsStatsService;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * Created by Radosław Osiński
// */
//@RestController
//public class AdminJobsRestController {
//
//    private final JobsStatsService jobsStatsService;
//
//    public AdminJobsRestController(JobsStatsService jobsStatsService) {
//        this.jobsStatsService = jobsStatsService;
//    }
//
//    @RequestMapping(value = "/authorized/rest/admin/jobs/stats", method = RequestMethod.GET,
//        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
//    )
//    public List<JobStat> getJobsStats() {
//        return jobsStatsService.getJobsStats();
//    }
//
//}
