package dance.joyfrom.db.dancer;

import dance.joyfrom.db.school.LessonTypeEntity;
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
//@NamedQuery(name = LessonProposalEntity.IS_AUTHORIZED_TO_ADD_LESSON, query = "select count(sr.id) > 0 FROM SchoolRoomEntity sr WHERE sr.id = :roomId and sr.schoolEntity.id in (select sae.schoolId from SchoolAdminEntity sae where sae.email = :email)")
@Table(name = "lesson_proposals")
public class LessonProposalEntity {

//    public static final String IS_AUTHORIZED_TO_ADD_LESSON = "LessonProposalEntity.IS_AUTHORIZED_TO_ADD_LESSON";

    private long id;
    private String email;
    private long lessonTypeId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private LessonTypeEntity lessonTypeEntity;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_proposals_s")
    @GenericGenerator(name = "lesson_proposals_s",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "optimizer", value = "pooled-lo"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "lesson_proposals_s"),
        }
    )
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "lesson_type_id", nullable = false)
    public long getLessonTypeId() {
        return lessonTypeId;
    }

    public void setLessonTypeId(long lessonTypeId) {
        this.lessonTypeId = lessonTypeId;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = false)
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_type_id", nullable = false, insertable = false, updatable = false)
    public LessonTypeEntity getLessonTypeEntity() {
        return lessonTypeEntity;
    }

    public void setLessonTypeEntity(LessonTypeEntity lessonTypeEntity) {
        this.lessonTypeEntity = lessonTypeEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LessonProposalEntity that = (LessonProposalEntity) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(email, that.email)
            .append(lessonTypeId, that.lessonTypeId)
            .append(startTime, that.startTime)
            .append(endTime, that.endTime)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(email)
            .append(lessonTypeId)
            .append(startTime)
            .append(endTime)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("email", email)
            .append("lessonTypeId", lessonTypeId)
            .append("startTime", startTime)
            .append("endTime", endTime)
            .toString();
    }

}
