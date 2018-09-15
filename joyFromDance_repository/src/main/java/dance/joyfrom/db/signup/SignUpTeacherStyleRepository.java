package dance.joyfrom.db.signup;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Radosław Osiński
 */
@Repository
public class SignUpTeacherStyleRepository {

    public void add(Session session, SignUpTeacherStyleEntity signUpTeacherStyleEntity) {
        session.save(signUpTeacherStyleEntity);
    }

    public void delete(Session session, String email) {
        Query query = session.getNamedQuery(SignUpTeacherStyleEntity.DELETE);
        query.setParameter("email", email);
        query.executeUpdate();
    }
}
