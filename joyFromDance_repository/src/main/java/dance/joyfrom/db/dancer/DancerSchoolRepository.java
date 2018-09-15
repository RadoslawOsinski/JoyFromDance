package dance.joyfrom.db.dancer;

import dance.joyfrom.db.school.SchoolAdminEntity;
import dance.joyfrom.db.school.SchoolEntity;
import dance.joyfrom.db.school.SchoolRoomEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Radosław Osiński
 */
@Repository
public class DancerSchoolRepository {

    public void add(Session session, DancerSchoolEntity dancerSchoolEntity) {
        session.saveOrUpdate(dancerSchoolEntity);
    }

    public Stream<DancerSchoolEntity> get(
        Session session, Integer currentPage, Integer displayedRows, String searchSchoolName, String email
    ) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<DancerSchoolEntity> query = criteriaBuilder.createQuery(DancerSchoolEntity.class);
        Root<DancerSchoolEntity> dancerSchools = query.from(DancerSchoolEntity.class);
        final Join<DancerSchoolEntity, SchoolEntity> schools = dancerSchools.join("school", JoinType.LEFT);
        query.select(dancerSchools);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.like(criteriaBuilder.lower(schools.get("name")), '%' + searchSchoolName.toLowerCase() + '%'),
                criteriaBuilder.like(criteriaBuilder.lower(dancerSchools.get("email")), '%' + email.toLowerCase() + '%')
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(schools.get("name")));
        query.orderBy(orders);
        return session.createQuery(query)
            .setFirstResult(currentPage * displayedRows)
            .setMaxResults(displayedRows)
            .stream();
    }

    public void delete(Session session, Long schoolId, String email) {
        Query query = session.getNamedQuery(SchoolAdminEntity.DELETE);
        query.setParameter("schoolId", schoolId);
        query.setParameter("email", email);
        query.executeUpdate();
    }

    public List<SchoolRoomEntity> getSchoolRooms(Session session, Long schoolId) {
        SchoolEntity school = session.get(SchoolEntity.class, schoolId);
        return school.getSchoolRooms();
    }

}
