package dance.joyfrom.db.user;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Radosław Osiński
 */
@Repository
public class UserRepository {

    public void add(Session session, UserEntity userEntity) {
        session.saveOrUpdate(userEntity);
    }

    public Optional<UserEntity> getByEmail(Session session, String userEmail) {
        Query query = session.getNamedQuery(UserEntity.GET_BY_EMAIL);
        query.setParameter("email", userEmail);
        return query.uniqueResultOptional();
    }

}
