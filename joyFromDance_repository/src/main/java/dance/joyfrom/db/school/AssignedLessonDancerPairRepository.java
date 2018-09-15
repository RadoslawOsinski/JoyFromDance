package dance.joyfrom.db.school;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Radosław Osiński
 */
@Repository
public class AssignedLessonDancerPairRepository {

    public void add(Session session, AssignedLessonDancerPairEntity assignedLessonDancerPairEntity) {
        session.saveOrUpdate(assignedLessonDancerPairEntity);
    }

    public Optional<AssignedLessonDancerPairEntity> getByIdAndEmail2(Session session, Long lessonId, String userEmail) {
        Query query = session.getNamedQuery(AssignedLessonDancerPairEntity.GET_BY_LESSON_AND_EMAIL2);
        query.setParameter("id", lessonId);
        query.setParameter("email2", userEmail);
        return query.uniqueResultOptional();
    }

    public void deleteOtherProposals(Session session, long lessonId, String email2, long pairId) {
        Query query = session.getNamedQuery(AssignedLessonDancerPairEntity.DELETE_OTHER_PROPOSALS);
        query.setParameter("roomLessonId", lessonId);
        query.setParameter("email2", email2);
        query.setParameter("pairId", pairId);
        query.executeUpdate();
    }
}
