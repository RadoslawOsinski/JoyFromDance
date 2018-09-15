package dance.joyfrom.rest.session;

//import org.keycloak.KeycloakPrincipal;
//import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import dance.joyfrom.db.user.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
public class UserDispatcherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDispatcherService.class);

    public static String getCurrentUserEmail() throws CredentialNotFoundException {
        return "rado@wp.pl";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof AnonymousAuthenticationToken) {
//            throw new CredentialNotFoundException();
//        }
//        KeycloakAuthenticationToken authorizedAuthentication = (KeycloakAuthenticationToken) authentication;
//        KeycloakPrincipal principal = (KeycloakPrincipal) authorizedAuthentication.getPrincipal();
//        return principal.getKeycloakSecurityContext().getToken().getEmail();
    }

    /**
     * @return First name and last name separated by space. In example: "FirstName LastName"
     */
    public static String getCurrentUserName() throws CredentialNotFoundException {
        return "u";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof AnonymousAuthenticationToken) {
//            throw new CredentialNotFoundException();
//        }
//        KeycloakAuthenticationToken authorizedAuthentication = (KeycloakAuthenticationToken) authentication;
//        KeycloakPrincipal principal = (KeycloakPrincipal) authorizedAuthentication.getPrincipal();
//        return principal.getKeycloakSecurityContext().getToken().getName();
    }

    /**
     * @return First name
     */
    public static String getCurrentUserFirstName() throws CredentialNotFoundException {
        return "name0";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof AnonymousAuthenticationToken) {
//            throw new CredentialNotFoundException();
//        }
//        KeycloakAuthenticationToken authorizedAuthentication = (KeycloakAuthenticationToken) authentication;
//        KeycloakPrincipal principal = (KeycloakPrincipal) authorizedAuthentication.getPrincipal();
//        return principal.getKeycloakSecurityContext().getToken().getGivenName();
    }

    /**
     * @return Last name
     */
    public static String getCurrentUserLastName() throws CredentialNotFoundException {
        return "last name";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof AnonymousAuthenticationToken) {
//            throw new CredentialNotFoundException();
//        }
//        KeycloakAuthenticationToken authorizedAuthentication = (KeycloakAuthenticationToken) authentication;
//        KeycloakPrincipal principal = (KeycloakPrincipal) authorizedAuthentication.getPrincipal();
//        return principal.getKeycloakSecurityContext().getToken().getFamilyName();
    }

    public static void addRoleToCurrentSession(Roles role) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof AnonymousAuthenticationToken) {
//            LOGGER.info("Cannot add role to current session. Role: {}", role);
//        } else {
//            KeycloakAuthenticationToken authorizedAuthentication = (KeycloakAuthenticationToken) authentication;
//            List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authorizedAuthentication.getAuthorities());
//            updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//            Authentication newAuth = new KeycloakAuthenticationToken(authorizedAuthentication.getAccount(), updatedAuthorities);
//            SecurityContextHolder.getContext().setAuthentication(newAuth);
//        }
    }

    public static void updateFirstAndLastNameInCurrentSession(String firstName, String lastName) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof AnonymousAuthenticationToken) {
//            LOGGER.info("Cannot update first and last name on current session. firstName: {}, lastName: {}", firstName, lastName);
//        } else {
//            KeycloakAuthenticationToken authorizedAuthentication = (KeycloakAuthenticationToken) authentication;
//            KeycloakPrincipal principal = (KeycloakPrincipal) authorizedAuthentication.getPrincipal();
//            principal.getKeycloakSecurityContext().getToken().setGivenName(firstName);
//            principal.getKeycloakSecurityContext().getToken().setFamilyName(lastName);
//            principal.getKeycloakSecurityContext().getIdToken().setName(firstName + " " + lastName);
//        }
    }

}
