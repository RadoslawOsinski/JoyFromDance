//package dance.joyfrom.jobs.stats;
//
//import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.core.Member;
//import com.hazelcast.scheduledexecutor.IScheduledExecutorService;
//import com.hazelcast.scheduledexecutor.IScheduledFuture;
//import com.hazelcast.scheduledexecutor.ScheduledTaskHandler;
//import com.hazelcast.scheduledexecutor.ScheduledTaskStatistics;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by Radosław Osiński
// */
//@Service
//public class JobsStatsService {
//
//    private static final String JOY_FROM_DANCE_JOB_SCHEDULER = "joyFromDanceJobScheduler";
//
//    private IScheduledExecutorService scheduledExecutorService;
//
//    public JobsStatsService(HazelcastInstance hazelcastInstance) {
//        this.scheduledExecutorService = hazelcastInstance.getScheduledExecutorService(JOY_FROM_DANCE_JOB_SCHEDULER);
//    }
//
//    public List<JobStat> getJobsStats() {
//        List<JobStat> jobStats = new ArrayList<>();
//        Map<Member, List<IScheduledFuture<Object>>> allScheduledFutures = scheduledExecutorService.getAllScheduledFutures();
//        for (Member member : allScheduledFutures.keySet()) {
//            List<IScheduledFuture<Object>> iScheduledFutures = allScheduledFutures.get(member);
//            iScheduledFutures.forEach(sf -> {
//                ScheduledTaskStatistics stats = sf.getStats();
//                JobStat jobStat = new JobStat();
//                ScheduledTaskHandler handler = sf.getHandler();
//                jobStat.setName(handler.getTaskName());
//                jobStat.setMember(member.getUuid());
//                jobStat.setLastIdleDuration(stats.getLastIdleTime(TimeUnit.MILLISECONDS));
//                jobStat.setTotalIdleDuration(stats.getTotalIdleTime(TimeUnit.MILLISECONDS));
//                jobStat.setLastRunDuration(stats.getLastRunDuration(TimeUnit.MILLISECONDS));
//                jobStat.setTotalRunDuration(stats.getTotalRunTime(TimeUnit.MILLISECONDS));
//                jobStat.setRuns(stats.getTotalRuns());
//                jobStats.add(jobStat);
//            });
//        }
//        return jobStats;
//    }
//
//}
