package dance.joyfrom.db.signup;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Radosław Osiński
 */
@Repository
public class SignUpDancerStyleRepository {

    public void add(Session session, SignUpDancerStyleEntity signUpDancerStyleEntity) {
        session.save(signUpDancerStyleEntity);
    }

    public void delete(Session session, String email) {
        Query query = session.getNamedQuery(SignUpDancerStyleEntity.DELETE);
        query.setParameter("email", email);
        query.executeUpdate();
    }
}
