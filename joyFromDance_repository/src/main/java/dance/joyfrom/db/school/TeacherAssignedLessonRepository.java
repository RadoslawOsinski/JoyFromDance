package dance.joyfrom.db.school;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Radosław Osiński
 */
@Repository
public class TeacherAssignedLessonRepository {

    public void add(Session session, TeacherAssignedLessonEntity teacherAssignedLessonEntity) {
        session.saveOrUpdate(teacherAssignedLessonEntity);
    }

    public Stream<RoomLessonEntity> get(Session session, Integer currentPage, Integer displayedRows, String searchSchoolName, String searchRoom, String searchDescription, String email) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<RoomLessonEntity> query = criteriaBuilder.createQuery(RoomLessonEntity.class);
        Root<RoomLessonEntity> roomLesson = query.from(RoomLessonEntity.class);
        final Join<RoomLessonEntity, SchoolRoomEntity> room = roomLesson.join("schoolRoomEntity", JoinType.LEFT);
        final Join<SchoolRoomEntity, SchoolEntity> school = room.join("schoolEntity", JoinType.LEFT);
        final Join<RoomLessonEntity, TeacherAssignedLessonEntity> teacherAssignedLesson = roomLesson.join("assignedTeachers", JoinType.LEFT);
        query.select(roomLesson);

        List<Predicate> wheres = new ArrayList<>();
        wheres.add(criteriaBuilder.greaterThanOrEqualTo(roomLesson.get("endTime"), ZonedDateTime.now()));
        wheres.add(criteriaBuilder.equal(teacherAssignedLesson.get("email"), email));
        if (searchSchoolName != null && !searchSchoolName.isEmpty()) {
            wheres.add(criteriaBuilder.like(criteriaBuilder.lower(school.get("name")), "%" + searchSchoolName.toLowerCase() + "%"));
        }
        if (searchRoom != null && !searchRoom.isEmpty()) {
            wheres.add(criteriaBuilder.like(criteriaBuilder.lower(room.get("name")), "%" + searchRoom.toLowerCase() + "%"));
        }
        if (searchDescription != null && !searchDescription.isEmpty()) {
            wheres.add(criteriaBuilder.like(criteriaBuilder.lower(roomLesson.get("description")), "%" + searchDescription.toLowerCase() + "%"));
        }

        query.where(
            criteriaBuilder.and(wheres.toArray(new Predicate[wheres.size()]))
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(roomLesson.get("endTime")));
        query.orderBy(orders);
        return session.createQuery(query)
            .setFirstResult(currentPage * displayedRows)
            .setMaxResults(displayedRows)
            .stream();
    }
}
