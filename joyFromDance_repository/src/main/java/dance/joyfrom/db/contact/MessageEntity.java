package dance.joyfrom.db.contact;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by Radosław Osiński
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = MessageEntity.DELETE_BY_ID, query = "DELETE FROM MessageEntity m WHERE m.id = :id")
@Table(name = "messages")
public class MessageEntity {

    public static final String DELETE_BY_ID = "MessageEntity.DELETE_BY_ID";

    private long id;
    private String name;
    private String email;
    private String message;
    private ZonedDateTime created;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_s")
    @GenericGenerator(name = "messages_s",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "optimizer", value = "pooled-lo"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "messages_s"),
        }
    )
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 200)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "message", nullable = false, length = 1000)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MessageEntity that = (MessageEntity) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(name, that.name)
            .append(email, that.email)
            .append(message, that.message)
            .append(created, that.created)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(email)
            .append(message)
            .append(created)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("name", name)
            .append("email", email)
            .append("message", message)
            .append("created", created)
            .toString();
    }
}
