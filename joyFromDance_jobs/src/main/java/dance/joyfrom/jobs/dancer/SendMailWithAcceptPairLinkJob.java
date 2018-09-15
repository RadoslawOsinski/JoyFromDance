package dance.joyfrom.jobs.dancer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Radosław Osiński
 */
public class SendMailWithAcceptPairLinkJob implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMailWithAcceptPairLinkJob.class);

    @Override
    public void run() {
        LOGGER.info("SendMailWithAcceptPairLinkJob job!");
    }

}
