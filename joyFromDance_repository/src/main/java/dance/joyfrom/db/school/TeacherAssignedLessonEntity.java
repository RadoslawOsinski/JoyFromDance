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
@Table(name = "teacher_assigned_lessons")
public class TeacherAssignedLessonEntity {

    private long id;
    private long roomLessonId;
    private String email;
    private RoomLessonEntity roomLesson;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_assigned_lessons_s")
    @GenericGenerator(name = "teacher_assigned_lessons_s",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "optimizer", value = "pooled-lo"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "teacher_assigned_lessons_s"),
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
    @Column(name = "email", nullable = false, length = 250)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

        TeacherAssignedLessonEntity that = (TeacherAssignedLessonEntity) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(roomLessonId, that.roomLessonId)
            .append(email, that.email)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(roomLessonId)
            .append(email)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("roomLessonId", roomLessonId)
            .append("email", email)
            .toString();
    }

}
