package dance.joyfrom.delivery.email;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Created by Radosław Osiński
 */
@RestController
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    //example usage: curl -X POST -d "{\"to\":\"rrr@wp.pl\",\"text\":\"t\"}" -H "Content-Type: application/json" https://localhost:8443/sendEmail
    @PostMapping("/sendEmail")
    public Mono<String> sendEmail(@Valid @RequestBody EmailMessage emailMessage) {
        emailService.sendEmail(emailMessage);
        return Mono.just("OK");
    }
}
