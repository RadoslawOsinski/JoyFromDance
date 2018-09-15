package dance.joyfrom.db.signup;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Radosław Osiński
 */
@Repository
public class SignUpDanceFloorOwnerRepository {

    public Optional<SignUpDanceFloorOwnerEntity> get(Session session, String email) {
        return session.byId(SignUpDanceFloorOwnerEntity.class).loadOptional(email);
    }

    public void add(Session session, SignUpDanceFloorOwnerEntity signUpTeacherEntity) {
        session.saveOrUpdate(signUpTeacherEntity);
    }

    public void delete(Session session, String email) {
        Query query = session.getNamedQuery(SignUpDanceFloorOwnerEntity.DELETE);
        query.setParameter("email", email);
        query.executeUpdate();
    }

    public void update(Session session, SignUpDanceFloorOwnerEntity signUpDanceFloorOwnerEntity) {
        session.merge(signUpDanceFloorOwnerEntity);
    }

}
