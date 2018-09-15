package dance.joyfrom.website.config;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
@Configuration
public class ServletConfiguration extends SpringBootServletInitializer implements WebMvcConfigurer {

    private LocaleChangeInterceptor getLocaleChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("joyFromDanceLanguage");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLocaleChangeInterceptor());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(Environment environment) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String[] allowedOrigins = environment.getProperty("joy.from.dance.website.cors.allowed.origins", String[].class, new String[]{"http://joyfrom.dance", "https://joyfrom.dance"});
                registry.addMapping("/**")
                    .allowedOrigins(allowedOrigins);
            }
        };
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
////        allowed-origins="${cors.allowed.origins}"
//        registry.addMapping("/rest/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*").allowCredentials(false);
//        super.addCorsMappings(registry);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources-joy-from-dance/**").addResourceLocations("/resources-joy-from-dance/");
        registry.addResourceHandler("/node_modules/**").addResourceLocations("/resources-joy-from-dance/node_modules/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Bean
    public StringHttpMessageConverter getStringHttpMessageConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }
}
