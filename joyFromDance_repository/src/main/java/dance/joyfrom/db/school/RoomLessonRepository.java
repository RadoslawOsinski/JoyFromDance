package dance.joyfrom.db.school;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Radosław Osiński
 */
@Repository
public class RoomLessonRepository {

    public void add(Session session, RoomLessonEntity roomLessonEntity) {
        session.saveOrUpdate(roomLessonEntity);
    }

    public void update(Session session, RoomLessonEntity roomLessonEntity) {
        session.saveOrUpdate(roomLessonEntity);
    }

    public RoomLessonEntity get(Session session, Long lessonId) {
        return session.get(RoomLessonEntity.class, lessonId);
    }

    public boolean isAuthorizedToAddLesson(Session session, String userEmail, Long roomId) {
        Query query = session.getNamedQuery(RoomLessonEntity.IS_AUTHORIZED_TO_ADD_LESSON);
        query.setParameter("roomId", roomId);
        query.setParameter("email", userEmail);
        return (boolean) query.uniqueResult();
    }

    public Stream<RoomLessonEntity> get(Session session, Long roomId, ZonedDateTime start, ZonedDateTime end) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<RoomLessonEntity> query = criteriaBuilder.createQuery(RoomLessonEntity.class);
        Root<RoomLessonEntity> params = query.from(RoomLessonEntity.class);
        query.select(params);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(params.get("roomId"), roomId),
                criteriaBuilder.greaterThanOrEqualTo(params.get("startTime"), start),
                criteriaBuilder.lessThanOrEqualTo(params.get("endTime"), end)
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(params.get("startTime")));
        query.orderBy(orders);
        return session.createQuery(query)
            .stream();
    }

//    public void delete(Session session, Long schoolId, String email) {
//        Query query = session.getNamedQuery(SchoolAdminEntity.DELETE);
//        query.setParameter("schoolId", schoolId);
//        query.setParameter("email", email);
//        query.executeUpdate();
//    }

}
