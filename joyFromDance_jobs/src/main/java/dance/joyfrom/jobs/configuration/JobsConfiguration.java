//package dance.joyfrom.jobs.configuration;
//
//import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.scheduledexecutor.IScheduledExecutorService;
//import com.hazelcast.scheduledexecutor.IScheduledFuture;
//import com.hazelcast.scheduledexecutor.TaskUtils;
//import dance.joyfrom.jobs.dancer.SendMailWithAcceptPairLinkJob;
//import dance.joyfrom.jobs.schooladmin.RemindLackOfPairsForTodayJob;
//import dance.joyfrom.jobs.schooladmin.SmallGroupOfDancersOnLessonJob;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by Radoslaw Osinski.
// */
//@Configuration
//public class JobsConfiguration {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(JobsConfiguration.class);
//
//    private static final String JOY_FROM_DANCE_JOB_SCHEDULER = "joyFromDanceJobScheduler";
//    private static final int INITIAL_DELAY_IN_SECONDS = 3;
//
//    private final Environment environment;
//    private final IScheduledExecutorService scheduledExecutorService;
//
//    public JobsConfiguration(Environment environment, HazelcastInstance hazelcastInstance) {
//        this.environment = environment;
//        this.scheduledExecutorService = hazelcastInstance.getScheduledExecutorService(JOY_FROM_DANCE_JOB_SCHEDULER);
//    }
//
//    @Bean("RemindLackOfPairsForTodayJobFuture")
//    public IScheduledFuture<?> scheduleRemindLackOfPairsForTodayJob() {
//        String jobName = RemindLackOfPairsForTodayJob.class.getName();
//        int period = environment.getProperty("remind.lack.of.pairs.for.today.period.in.minutes", Integer.class, 10);
//        TimeUnit timeUnit = TimeUnit.MINUTES;
//        LOGGER.info("Scheduling job {} at fixed rate for period: {}, timeUnit: {}", jobName, period, timeUnit);
//        return scheduledExecutorService.scheduleOnKeyOwnerAtFixedRate(
//            TaskUtils.named(jobName, new RemindLackOfPairsForTodayJob()),
//            jobName,
//            INITIAL_DELAY_IN_SECONDS,
//            period,
//            timeUnit
//        );
//    }
//
//    @Bean("SmallGroupOfDancersOnLessonJobFuture")
//    public IScheduledFuture<?> scheduleSmallGroupOfDancersOnLessonJob() {
//        String jobName = SmallGroupOfDancersOnLessonJob.class.getName();
//        int period = environment.getProperty("small.group.of.dancers.on.lesson.period.in.hours", Integer.class, 12);
//        TimeUnit timeUnit = TimeUnit.HOURS;
//        LOGGER.info("Scheduling job {} at fixed rate for period: {}, timeUnit: {}", jobName, period, timeUnit);
//        return scheduledExecutorService.scheduleOnKeyOwnerAtFixedRate(
//            TaskUtils.named(jobName, new SmallGroupOfDancersOnLessonJob()),
//            jobName,
//            INITIAL_DELAY_IN_SECONDS,
//            period,
//            timeUnit
//        );
//    }
//
//    @Bean("SendMailWithAcceptPairLinkJobFuture")
//    public IScheduledFuture<?> scheduleSendMailWithAcceptPairLinkJob() {
//        String jobName = SendMailWithAcceptPairLinkJob.class.getName();
//        int period = environment.getProperty("send.mail.with.accept.pair.link.in.minutes", Integer.class, 5);
//        TimeUnit timeUnit = TimeUnit.MINUTES;
//        LOGGER.info("Scheduling job {} at fixed rate for period: {}, timeUnit: {}", jobName, period, timeUnit);
//        return scheduledExecutorService.scheduleOnKeyOwnerAtFixedRate(
//            TaskUtils.named(jobName, new SendMailWithAcceptPairLinkJob()),
//            jobName,
//            INITIAL_DELAY_IN_SECONDS,
//            period,
//            timeUnit
//        );
//    }
//
//}
