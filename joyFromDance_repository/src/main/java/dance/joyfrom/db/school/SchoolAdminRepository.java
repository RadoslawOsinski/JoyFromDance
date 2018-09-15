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
import java.util.stream.Stream;

/**
 * Created by Radosław Osiński
 */
@Repository
public class SchoolAdminRepository {

    public void add(Session session, SchoolAdminEntity schoolAdminEntity) {
        session.saveOrUpdate(schoolAdminEntity);
    }

    public Stream<SchoolAdminEntity> get(
        Session session, Long schoolId
    ) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<SchoolAdminEntity> query = criteriaBuilder.createQuery(SchoolAdminEntity.class);
        Root<SchoolAdminEntity> params = query.from(SchoolAdminEntity.class);
        query.select(params);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(params.get("schoolId"), schoolId)
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(params.get("email")));
        query.orderBy(orders);
        return session.createQuery(query)
            .stream();
    }

    public void delete(Session session, Long schoolId, String email) {
        Query query = session.getNamedQuery(SchoolAdminEntity.DELETE);
        query.setParameter("schoolId", schoolId);
        query.setParameter("email", email);
        query.executeUpdate();
    }

    public Stream<SchoolAdminEntity> getAdminSchools(Session session, String email) {
        Query query = session.getNamedQuery(SchoolAdminEntity.GET_ADMIN_SCHOOLS);
        query.setParameter("email", email);
        return query.stream();
    }

    public void deleteBySchoolId(Session session, Long schoolId) {
        Query query = session.getNamedQuery(SchoolAdminEntity.DELETE_BY_SCHOOL_ID);
        query.setParameter("schoolId", schoolId);
        query.executeUpdate();
    }
}
