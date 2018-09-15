package dance.joyfrom.db.contact;

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
public class MessageRepository {

    public Long add(Session session, MessageEntity messageEntity) {
        session.saveOrUpdate(messageEntity);
        return messageEntity.getId();
    }

    public Stream<MessageEntity> get(
        Session session, Integer currentPage, Integer displayedRows, String name, String email
    ) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<MessageEntity> query = criteriaBuilder.createQuery(MessageEntity.class);
        Root<MessageEntity> params = query.from(MessageEntity.class);
        query.select(params);
        query.where(
            criteriaBuilder.and(
                criteriaBuilder.like(criteriaBuilder.lower(params.get("name")), "%" + name.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(params.get("email")), "%" + email.toLowerCase() + "%")
            )
        );
        List<Order> orders = new ArrayList<>();
        orders.add(criteriaBuilder.asc(params.get("created")));
        query.orderBy(orders);
        return session.createQuery(query)
            .setFirstResult(currentPage * displayedRows)
            .setMaxResults(displayedRows)
            .stream();
    }

    public void delete(Session session, Long messageId) {
        Query namedQuery = session.getNamedQuery(MessageEntity.DELETE_BY_ID);
        namedQuery.setParameter("id", messageId);
        namedQuery.executeUpdate();
    }
}
