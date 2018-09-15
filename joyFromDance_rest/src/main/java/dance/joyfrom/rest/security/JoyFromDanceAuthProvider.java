package dance.joyfrom.rest.security;

import dance.joyfrom.services.user.User;
import dance.joyfrom.services.user.UserRole;
import dance.joyfrom.services.user.UserRolesService;
import dance.joyfrom.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Radoslaw Osinski
 */
@Component
public class JoyFromDanceAuthProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JoyFromDanceAuthProvider.class);

    private final UserService userService;
    private final UserRolesService userRolesService;
    private final PasswordEncoder passwordEncoder;

    public JoyFromDanceAuthProvider(UserService userService, UserRolesService userRolesService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRolesService = userRolesService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication auth) {
        WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) auth.getDetails();
        String userIPAddress = webAuthenticationDetails.getRemoteAddress();
        LOGGER.debug("User login: {}, IP Address: {}", auth.getPrincipal(), userIPAddress);

        final Object password = auth.getCredentials();
        Optional<User> user = userService.getByEmail((String) auth.getPrincipal());
        if (user.isPresent()) {
            if (passwordEncoder.matches(String.valueOf(password), user.get().getPasswordHash())) {
                final List<UserRole> roles = userRolesService.listUserRoles(user.get().getId());
                List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
                for (UserRole role: roles) {
                    authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
                }
                LOGGER.info("User {} has granted access with roles: {}", auth.getPrincipal(), roles.stream().map(UserRole::getRole).collect(Collectors.toList()));
                return new JoyFromDanceAuthenticationToken(auth.getName(), password, authorities);
            } else {
                LOGGER.error("User {} password is incorrect", auth.getPrincipal());
            }
        } else {
            LOGGER.error("User {} does not exist", auth.getPrincipal());
        }
        throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
