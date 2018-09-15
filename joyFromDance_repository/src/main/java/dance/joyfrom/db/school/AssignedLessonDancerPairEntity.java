package dance.joyfrom.db.school;

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

/**
 * Created by Radosław Osiński
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.hibernate.annotations.NamedQuery(name = AssignedLessonDancerPairEntity.GET_BY_LESSON_AND_EMAIL2, query = "select dp FROM AssignedLessonDancerPairEntity dp WHERE dp.id = :id and dp.email2 = :email2")
@org.hibernate.annotations.NamedQuery(name = AssignedLessonDancerPairEntity.DELETE_OTHER_PROPOSALS, query = "delete FROM AssignedLessonDancerPairEntity dp WHERE dp.roomLessonId = :roomLessonId and dp.email2 = :email2 and id != :pairId")
@Table(name = "assigned_lesson_dancer_pairs")
public class AssignedLessonDancerPairEntity {

    public static final String GET_BY_LESSON_AND_EMAIL2 = "AssignedLessonDancerPairEntity.GET_BY_LESSON_AND_EMAIL2";
    public static final String DELETE_OTHER_PROPOSALS = "AssignedLessonDancerPairEntity.DELETE_OTHER_PROPOSALS";

    private long id;
    private long roomLessonId;
    private String email1;
    private String email2;
    private DancerPairStatus status;
    private RoomLessonEntity roomLesson;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assigned_lesson_dancer_pairs_s")
    @GenericGenerator(name = "assigned_lesson_dancer_pairs_s",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "optimizer", value = "pooled-lo"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "assigned_lesson_dancer_pairs_s"),
        }
    )
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "room_lesson_id", nullable = false)
    public long getRoomLessonId() {
        return roomLessonId;
    }

    public void setRoomLessonId(long roomLessonId) {
        this.roomLessonId = roomLessonId;
    }

    @Basic
    @Column(name = "email_1", nullable = false, length = 250)
    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    @Basic
    @Column(name = "email_2", nullable = false, length = 250)
    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public DancerPairStatus getStatus() {
        return status;
    }

    public void setStatus(DancerPairStatus status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_lesson_id", nullable = false, insertable = false, updatable = false)
    public RoomLessonEntity getRoomLesson() {
        return roomLesson;
    }

    public void setRoomLesson(RoomLessonEntity roomLesson) {
        this.roomLesson = roomLesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AssignedLessonDancerPairEntity that = (AssignedLessonDancerPairEntity) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(roomLessonId, that.roomLessonId)
            .append(email1, that.email1)
            .append(email2, that.email2)
            .append(status, that.status)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(roomLessonId)
            .append(email1)
            .append(email2)
            .append(status)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("roomLessonId", roomLessonId)
            .append("email1", email1)
            .append("email2", email2)
            .append("status", status)
            .toString();
    }

}
