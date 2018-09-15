package dance.joyfrom.rest.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by Radosław Osiński
 */
public class I18nValidationService {

    private static final Logger LOG = LoggerFactory.getLogger(I18nValidationService.class);

    private static final String VALIDATION_RESOURCE_BASENAME = "joy_from_dance_validation_i18n";

    public static String i18n(String key, Locale locale) {
        String i18nText;
        try {
            i18nText = ResourceBundle.getBundle(VALIDATION_RESOURCE_BASENAME, locale).getString("SchoolNameMustContainFrom1To200Characters");
        } catch (MissingResourceException e) {
            LOG.warn("Missing i18n with key: {} for locale: {}", key, locale);
            i18nText = "Missing i18n with key " + key;
        }
        return i18nText;
    }
}
