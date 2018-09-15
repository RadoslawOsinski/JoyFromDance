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
public class SchoolRoomRepository {

    public Long add(Session session, SchoolRoomEntity schoolRoomEntity) {
        session.saveOrUpdate(schoolRoomEntity);
        return schoolRoomEntity.getId();
    }

    public Stream<SchoolRoomEntity> get(
        Session session, Integer currentPage, Integer displayedRows, Long schoolId, String name
    ) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<SchoolRoomEntity> query = criteriaBuilder.createQuery(SchoolRoomEntity.class);
        Root<SchoolRoomEntity> params = query.from(SchoolRoomEntity.class);
        query.select(params);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(params.get("schoolId"), schoolId),
                criteriaBuilder.like(criteriaBuilder.lower(params.get("name")), "%" + name.toLowerCase() + "%")
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(params.get("name")));
        query.orderBy(orders);
        return session.createQuery(query)
            .setFirstResult(currentPage * displayedRows)
            .setMaxResults(displayedRows)
            .stream();
    }

    public void delete(Session currentSession, Long schoolId, String name) {
        //todo implement me
    }
}
