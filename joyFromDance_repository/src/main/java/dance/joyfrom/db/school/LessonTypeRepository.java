package dance.joyfrom.db.school;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Radosław Osiński
 */
@Repository
public class LessonTypeRepository {

    public void add(Session session, LessonTypeEntity lessonTypeEntity) {
        session.saveOrUpdate(lessonTypeEntity);
    }

    public void update(Session session, LessonTypeEntity lessonTypeEntity) {
        session.saveOrUpdate(lessonTypeEntity);
    }

    public LessonTypeEntity get(Session session, Long id) {
        return session.get(LessonTypeEntity.class, id);
    }

    public Stream<LessonTypeEntity> get(Session session, Integer currentPage, Integer displayedRows, Long schoolId, String name) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<LessonTypeEntity> query = criteriaBuilder.createQuery(LessonTypeEntity.class);
        Root<LessonTypeEntity> lessonTypes = query.from(LessonTypeEntity.class);
        query.select(lessonTypes);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(lessonTypes.get("schoolId"), schoolId),
                criteriaBuilder.like(criteriaBuilder.lower(lessonTypes.get("name")), '%' + name.toLowerCase() + '%')
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(lessonTypes.get("name")));
        query.orderBy(orders);
        return session.createQuery(query)
            .setFirstResult(currentPage * displayedRows)
            .setMaxResults(displayedRows)
            .stream();
    }

    public Stream<LessonTypeEntity> getBySchoolId(Session session, Long schoolId) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<LessonTypeEntity> query = criteriaBuilder.createQuery(LessonTypeEntity.class);
        Root<LessonTypeEntity> lessonTypes = query.from(LessonTypeEntity.class);
        query.select(lessonTypes);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(lessonTypes.get("schoolId"), schoolId)
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(lessonTypes.get("name")));
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
