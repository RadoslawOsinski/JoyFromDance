package dance.joyfrom.db.signup;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Radosław Osiński
 */
@Repository
public class SignUpTeacherRepository {

    public Optional<SignUpTeacherEntity> get(Session session, String email) {
        return session.byId(SignUpTeacherEntity.class).loadOptional(email);
    }

    public void add(Session session, SignUpTeacherEntity signUpTeacherEntity) {
        session.saveOrUpdate(signUpTeacherEntity);
    }

    public void delete(Session session, String email) {
        Query query = session.getNamedQuery(SignUpTeacherEntity.DELETE);
        query.setParameter("email", email);
        query.executeUpdate();
    }

    public void update(Session session, SignUpTeacherEntity signUpTeacherEntity) {
        session.merge(signUpTeacherEntity);
    }

}
