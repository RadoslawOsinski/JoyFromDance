package dance.joyfrom.rest.security;

import com.hazelcast.core.HazelcastInstance;
import dance.joyfrom.services.user.UserRolesService;
import dance.joyfrom.services.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

@EnableWebSecurity
@Configuration
public class JoyFromDanceSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final UserRolesService userRolesService;
    private final HazelcastInstance hazelcastInstance;

    public JoyFromDanceSecurityConfig(UserService userService, UserRolesService userRolesService, HazelcastInstance hazelcastInstance) {
        this.userService = userService;
        this.userRolesService = userRolesService;
        this.hazelcastInstance = hazelcastInstance;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new DistributedSessionRegistry(hazelcastInstance);
    }

    @Bean
    public PasswordEncoder delegatingPasswordEncoder() {
        PasswordEncoder defaultEncoder = new BCryptPasswordEncoder();
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);

        return passwordEncoder;
    }

    @Bean
    public AuthenticationProvider authProvider() {
        return new JoyFromDanceAuthProvider(userService, userRolesService, delegatingPasswordEncoder());
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() {
        List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
        authenticationProviders.add(authProvider());
        return new ProviderManager(authenticationProviders);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
            .cors().and()
            .csrf().disable() //todo enable this!
            .authorizeRequests()
            .antMatchers("/loginz", "/logout", "/registration", "/rest/public/**").permitAll()
//            .antMatchers("/admin/**").hasRole("ADMIN")
//            .antMatchers("/schooladmin/**").hasAnyRole("SCHOOL_ADMIN", "ADMIN")
//            .antMatchers("/teacher/**").hasAnyRole("TEACHER", "ADMIN")
//            .antMatchers("/dancer/**").hasAnyRole("DANCER", "ADMIN")
//            .antMatchers("/dance/**").hasAnyRole("DANCER", "SCHOOL_ADMIN", "TEACHER", "ADMIN")

            .antMatchers("/authorized/rest/admin/**").hasRole("ADMIN")
            .antMatchers("/authorized/rest/schooladmin/**").hasAnyRole("SCHOOL_ADMIN", "ADMIN")
            .antMatchers("/authorized/rest/teacher/**").hasAnyRole("TEACHER", "ADMIN")
            .antMatchers("/authorized/rest/dancer/**").hasAnyRole("DANCER", "ADMIN")
            .antMatchers("/authorized/rest/dance/**").hasAnyRole("DANCER", "SCHOOL_ADMIN", "TEACHER", "ADMIN")

            .anyRequest().permitAll()

            .and()
            .authenticationProvider(authProvider())
            .formLogin().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(Environment environment) {
        CorsConfiguration configuration = new CorsConfiguration();
        String[] allowedOrigins = environment.getProperty("joy.from.dance.website.cors.allowed.origins", String[].class, new String[]{"http://joyfrom.dance", "https://joyfrom.dance"});
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Arrays.asList(
            "Content-Type", "content-type", "x-requested-with",
            "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Origin", "Accept",
            "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"
        ));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//            .antMatchers("/resources-joy-from-dance/**")
//            .antMatchers("/rest/**");
//    }

}

