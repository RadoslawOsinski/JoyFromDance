package dance.joyfrom.db.dancer;

import dance.joyfrom.db.school.LessonTypeEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Radosław Osiński
 */
@Repository
public class LessonProposalsRepository {

    public void add(Session session, LessonProposalEntity lessonProposalEntity) {
        session.saveOrUpdate(lessonProposalEntity);
    }

    public Stream<LessonProposalEntity> get(Session session, String userEmail) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<LessonProposalEntity> query = criteriaBuilder.createQuery(LessonProposalEntity.class);
        Root<LessonProposalEntity> lessonProposal = query.from(LessonProposalEntity.class);
        query.select(lessonProposal);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(criteriaBuilder.lower(lessonProposal.get("email")), userEmail.toLowerCase())
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(lessonProposal.get("startTime")));
        query.orderBy(orders);
        return session.createQuery(query)
            .stream();
    }

    public Stream<LessonProposalEntity> get(Session session, Long schoolId, String lessonTypeName) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<LessonProposalEntity> query = criteriaBuilder.createQuery(LessonProposalEntity.class);
        Root<LessonProposalEntity> lessonProposal = query.from(LessonProposalEntity.class);
        final Join<LessonProposalEntity, LessonTypeEntity> lessonTypes = lessonProposal.join("lessonTypeEntity", JoinType.LEFT);
        query.select(lessonProposal);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.equal(lessonTypes.get("schoolId"), schoolId),
                criteriaBuilder.like(criteriaBuilder.lower(lessonTypes.get("name")), '%' + lessonTypeName.toLowerCase() + '%')
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(lessonTypes.get("name")));
        query.orderBy(orders);
        return session.createQuery(query)
            .stream();
    }
}
