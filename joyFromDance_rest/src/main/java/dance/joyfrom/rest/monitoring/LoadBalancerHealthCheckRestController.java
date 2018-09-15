package dance.joyfrom.rest.monitoring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Radosław Osiński
 */
@RestController
public class LoadBalancerHealthCheckRestController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void healthCheck() {
    }

}
