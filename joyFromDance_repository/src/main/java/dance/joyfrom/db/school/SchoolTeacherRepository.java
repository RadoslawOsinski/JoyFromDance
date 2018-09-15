package dance.joyfrom.db.school;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
@Repository
public class SchoolTeacherRepository {

    public void add(Session session, SchoolTeacherEntity schoolTeacherEntity) {
        session.saveOrUpdate(schoolTeacherEntity);
    }

    public List<SchoolTeacherEntity> get(
        Session session, Integer currentPage, Integer displayedRows, Long schoolId, String email, Boolean ascendingEmailSortOrder
    ) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<SchoolTeacherEntity> query = criteriaBuilder.createQuery(SchoolTeacherEntity.class);
        Root<SchoolTeacherEntity> teachers = query.from(SchoolTeacherEntity.class);
        query.select(teachers);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(teachers.get("schoolId"), schoolId),
                criteriaBuilder.like(criteriaBuilder.lower(teachers.get("email")), '%' + email.toLowerCase() + '%')
            )
        );
        List<Order> orders = new ArrayList<>();
        if (ascendingEmailSortOrder) {
            orders.add(criteriaBuilder.asc(teachers.get("email")));
        } else {
            orders.add(criteriaBuilder.desc(teachers.get("email")));
        }
        query.orderBy(orders);
        return session.createQuery(query)
            .setFirstResult(currentPage * displayedRows)
            .setMaxResults(displayedRows)
            .list();
    }

    public Long getTotalNumber(Session session, Long schoolId, String email) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<SchoolTeacherEntity> teachers = query.from(SchoolTeacherEntity.class);
        query.select(criteriaBuilder.count(teachers));
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(teachers.get("schoolId"), schoolId),
                criteriaBuilder.like(criteriaBuilder.lower(teachers.get("email")), '%' + email.toLowerCase() + '%')
            )
        );
        return session.createQuery(query).uniqueResult();
    }

    public void delete(Session session, Long schoolId, String email) {
        Query query = session.getNamedQuery(SchoolAdminEntity.DELETE);
        query.setParameter("schoolId", schoolId);
        query.setParameter("email", email);
        query.executeUpdate();
    }

}
