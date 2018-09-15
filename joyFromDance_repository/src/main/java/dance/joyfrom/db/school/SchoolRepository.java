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
public class SchoolRepository {

    public Long add(Session session, SchoolEntity schoolEntity) {
        session.saveOrUpdate(schoolEntity);
        return schoolEntity.getId();
    }

    public List<SchoolEntity> getOwned(
        Session session, Integer currentPage, Integer displayedRows, String ownerEmail, String name
    ) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<SchoolEntity> query = criteriaBuilder.createQuery(SchoolEntity.class);
        Root<SchoolEntity> params = query.from(SchoolEntity.class);
        query.select(params);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.like(criteriaBuilder.lower(params.get("ownerEmail")), ownerEmail.toLowerCase()),
                criteriaBuilder.like(criteriaBuilder.lower(params.get("name")), "%" + name.toLowerCase() + "%")
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(params.get("created")));
        query.orderBy(orders);
        return session.createQuery(query)
            .setFirstResult(currentPage * displayedRows)
            .setMaxResults(displayedRows)
            .list();
    }

    public Long getOwnedTotalNumber(Session session, String ownerEmail, String name) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<SchoolEntity> params = query.from(SchoolEntity.class);
        query.select(criteriaBuilder.count(params));
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.like(criteriaBuilder.lower(params.get("ownerEmail")), ownerEmail.toLowerCase()),
                criteriaBuilder.like(criteriaBuilder.lower(params.get("name")), "%" + name.toLowerCase() + "%")
            )
        );
        return session.createQuery(query).uniqueResult();
    }

    public List<SchoolEntity> get(
        Session session, Integer currentPage, Integer displayedRows, String name
    ) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<SchoolEntity> query = criteriaBuilder.createQuery(SchoolEntity.class);
        Root<SchoolEntity> params = query.from(SchoolEntity.class);
        query.select(params);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.like(criteriaBuilder.lower(params.get("name")), "%" + name.toLowerCase() + "%")
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(params.get("created")));
        query.orderBy(orders);
        return session.createQuery(query)
            .setFirstResult(currentPage * displayedRows)
            .setMaxResults(displayedRows)
            .list();
    }

    public Long getTotalNumber(Session session, String ownerEmail, String name) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<SchoolEntity> params = query.from(SchoolEntity.class);
        query.select(criteriaBuilder.count(params));
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.like(criteriaBuilder.lower(params.get("name")), "%" + name.toLowerCase() + "%")
            )
        );
        return session.createQuery(query).uniqueResult();
    }

    public void delete(Session session, Long schoolId) {
        Query query = session.getNamedQuery(SchoolEntity.DELETE_BY_ID);
        query.setParameter("id", schoolId);
        query.executeUpdate();
    }

    public boolean isSchoolOwner(Session session, Long schoolId, String ownerEmail) {
        Query<Long> query = (Query<Long>) session.getNamedQuery(SchoolEntity.IS_SCHOOL_OWNER);
        query.setParameter("schoolId", schoolId);
        query.setParameter("ownerEmail", ownerEmail);
        return query.uniqueResult() > 0;
    }
}
