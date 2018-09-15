package dance.joyfrom.db.signup;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Radosław Osiński
 */
@Repository
public class SignUpDancerRepository {

    public Optional<SignUpDancerEntity> get(Session session, String email) {
        return session.byId(SignUpDancerEntity.class).loadOptional(email);
    }

    public void add(Session session, SignUpDancerEntity signUpDancerEntity) {
        session.saveOrUpdate(signUpDancerEntity);
    }

    public void delete(Session session, String email) {
        Query query = session.getNamedQuery(SignUpDancerEntity.DELETE);
        query.setParameter("email", email);
        query.executeUpdate();
    }

    public void update(Session session, SignUpDancerEntity signUpDancerEntity) {
        session.merge(signUpDancerEntity);
    }

}
