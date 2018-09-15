package dance.joyfrom.jobs.schooladmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Radosław Osiński
 */
public class RemindLackOfPairsForTodayJob implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemindLackOfPairsForTodayJob.class);

    @Override
    public void run() {
        LOGGER.info("RemindLackOfPairsForTodayJob job!");
    }

}
