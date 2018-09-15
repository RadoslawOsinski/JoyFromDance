package dance.joyfrom.website.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Radoslaw Osinski.
 */
@Controller
public class HTTPErrorController {

    @RequestMapping(value="/errors/404")
    public String handle404() {
        return "errors/404";
    }

}
