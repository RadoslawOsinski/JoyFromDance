package dance.joyfrom.website.main;

import dance.joyfrom.website.GenericController;
import dance.joyfrom.website.model.Keyword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Radoslaw Osinski
 */
@Controller
public class MainController extends GenericController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    private final Environment environment;

    public MainController(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showMainPage(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        return "main/Main";
    }

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String showMainPageByIndexHtml(ModelMap model, Locale locale) {
        setPageMetadata(model, locale);
        return "main/Main";
    }

    private void setPageMetadata(ModelMap model, Locale locale) {
        model.addAttribute("headerPageTitle", "Joy from dance");
        model.addAttribute("keywords", setPageKeywords(locale));
        model.addAttribute("additionalCssCode", setAdditionalCss());
        model.addAttribute("mainJavaScript", "/resources-joy-from-dance/js/Main.js");
        model.addAttribute("backendRestAddress", environment.getProperty("joy.from.dance.rest.url", "https://rest.joyfrom.dance"));
        model.addAttribute("googleApiKey", environment.getProperty("joy.from.dance.website.google.api.key", ""));
        model.addAttribute("gtag", environment.getProperty("joy.from.dance.website.gtag", ""));
    }

    private List<Keyword> setPageKeywords(Locale locale) {
        List<Keyword> keywords = new ArrayList<>();
        keywords.add(new Keyword(ResourceBundle.getBundle(RESOURCE_BUNDLE, locale).getString("DancingLessons")));
        return keywords;
    }

    private List<String> setAdditionalCss() {
        return new ArrayList<>();
    }

}
