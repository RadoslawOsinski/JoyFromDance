package dance.joyfrom.jobs.schooladmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Radosław Osiński
 */
public class SmallGroupOfDancersOnLessonJob implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmallGroupOfDancersOnLessonJob.class);

    @Override
    public void run() {
        LOGGER.info("SmallGroupOfDancersOnLessonJob job!");
    }

}
