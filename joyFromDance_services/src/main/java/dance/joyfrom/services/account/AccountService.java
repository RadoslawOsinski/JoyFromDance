package dance.joyfrom.services.account;

import dance.joyfrom.db.user.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Radosław Osiński
 */
@Service
public class AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);

    public void updateBasicAccountInfo(String userEmail, String firstName, String lastName) {
        LOG.info("User is updating basic account information. Email: {}, firstName: {}, lastName: {}", userEmail, firstName, lastName);
        //todo
//        UsersResource userResource = joyFromDanceRealmResource.users();
//        List<UserRepresentation> foundedUser = userResource.search(null, null, null, email, 0, 1);
//        if (foundedUser.isEmpty()) {
//            throw new RuntimeException("Keycloak user not found by email: " + email);
//        } else {
//            UserRepresentation userRepresentation = userResource.get(foundedUser.get(0).getId()).toRepresentation();
//            userRepresentation.setFirstName(firstName);
//            userRepresentation.setLastName(lastName);
//            userResource.get(foundedUser.get(0).getId()).update(userRepresentation);
//        }
    }

    public void addUserRole(String email, Roles role) {
        LOG.info("Adding role {} to user: {}", role, email);
        //todo
//        ClientRepresentation joyFromDanceClient = joyFromDanceRealmResource.clients().findByClientId("joyFromDance").get(0);
//        RoleRepresentation roleRepresentation = joyFromDanceRealmResource.clients().get(joyFromDanceClient.getId()).roles().get(role.name()).toRepresentation();
//        UsersResource userResource = joyFromDanceRealmResource.users();
//        List<UserRepresentation> foundedUser = userResource.search(null, null, null, email, 0, 1);
//        if (foundedUser.isEmpty()) {
//            throw new RuntimeException("Keycloak user not found by email: " + email);
//        } else {
//            userResource.get(foundedUser.get(0).getId()).roles().clientLevel(joyFromDanceClient.getId()).add(Collections.singletonList(roleRepresentation));
//            keycloak.tokenManager().refreshToken();
//        }
    }

    public void removeUserRole(String email, Roles role) {
        LOG.info("Removing role {} from user: {}", role, email);
        //todo
//        ClientRepresentation joyFromDanceClient = joyFromDanceRealmResource.clients().findByClientId("joyFromDance").get(0);
//        RoleRepresentation roleRepresentation = joyFromDanceRealmResource.clients().get(joyFromDanceClient.getId()).roles().get(role.name()).toRepresentation();
//        UsersResource userResource = joyFromDanceRealmResource.users();
//        List<UserRepresentation> foundedUser = userResource.search(null, null, null, email, 0, 1);
//        if (foundedUser.isEmpty()) {
//            throw new RuntimeException("Keycloak user not found by email: " + email);
//        } else {
//            userResource.get(foundedUser.get(0).getId()).roles().clientLevel(joyFromDanceClient.getId()).remove(Collections.singletonList(roleRepresentation));
//        }
    }

}
