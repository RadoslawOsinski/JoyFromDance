package dance.joyfrom.delivery.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Radosław Osiński
 */
@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    void sendEmail(EmailMessage emailMessage) {
        LOGGER.info("Mail to {} has been send: {}", emailMessage.getTo(), emailMessage.getText());
    }

}
