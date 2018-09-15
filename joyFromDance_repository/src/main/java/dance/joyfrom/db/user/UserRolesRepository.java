package dance.joyfrom.db.user;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Radosław Osiński
 */
@Repository
public class UserRolesRepository {

    public List<UserRoleEntity> listUserRoles(Session session, long userId) {
        Query query = session.getNamedQuery(UserRoleEntity.GET_USER_ROLES);
        query.setParameter("userId", userId);
        return query.list();
    }
}
