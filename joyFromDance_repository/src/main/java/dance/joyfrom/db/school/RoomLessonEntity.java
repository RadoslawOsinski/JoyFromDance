package dance.joyfrom.db.school;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = RoomLessonEntity.IS_AUTHORIZED_TO_ADD_LESSON, query = "select count(sr.id) > 0 FROM SchoolRoomEntity sr WHERE sr.id = :roomId and sr.schoolEntity.id in (select sae.schoolId from SchoolAdminEntity sae where sae.email = :email)")
@Table(name = "room_lessons")
public class RoomLessonEntity {

    public static final String IS_AUTHORIZED_TO_ADD_LESSON = "RoomLessonEntity.IS_AUTHORIZED_TO_ADD_LESSON";

    private long id;
    private long roomId;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String description;
    private String color;
    private String textColor;
    private Boolean published;
    private Boolean pairsRequired;
    private SchoolRoomEntity schoolRoomEntity;
    private List<TeacherAssignedLessonEntity> assignedTeachers;
    private List<DancerAssignedLessonEntity> assignedDancers;
    private List<AssignedLessonDancerPairEntity> dancerPairs;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_lessons_s")
    @GenericGenerator(name = "room_lessons_s",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "optimizer", value = "pooled-lo"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "room_lessons_s"),
        }
    )
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "room_id", nullable = false)
    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
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

    @Basic
    @Column(name = "description", nullable = false, length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "color", nullable = false, length = 50)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Basic
    @Column(name = "text_color", nullable = false, length = 50)
    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    @Basic
    @Column(name = "published")
    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getPublished() {
        return published;
    }

    @Basic
    @Column(name = "pairs_required")
    public Boolean getPairsRequired() {
        return pairsRequired;
    }

    public void setPairsRequired(Boolean pairsRequired) {
        this.pairsRequired = pairsRequired;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false, insertable = false, updatable = false)
    public SchoolRoomEntity getSchoolRoomEntity() {
        return schoolRoomEntity;
    }

    public void setSchoolRoomEntity(SchoolRoomEntity schoolRoomEntity) {
        this.schoolRoomEntity = schoolRoomEntity;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomLesson")
    public List<TeacherAssignedLessonEntity> getAssignedTeachers() {
        return assignedTeachers;
    }

    public void setAssignedTeachers(List<TeacherAssignedLessonEntity> assignedTeachers) {
        this.assignedTeachers = assignedTeachers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomLesson")
    public List<DancerAssignedLessonEntity> getAssignedDancers() {
        return assignedDancers;
    }

    public void setAssignedDancers(List<DancerAssignedLessonEntity> assignedDancers) {
        this.assignedDancers = assignedDancers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomLesson")
    public List<AssignedLessonDancerPairEntity> getDancerPairs() {
        return dancerPairs;
    }

    public void setDancerPairs(List<AssignedLessonDancerPairEntity> dancerPairs) {
        this.dancerPairs = dancerPairs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RoomLessonEntity that = (RoomLessonEntity) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(roomId, that.roomId)
            .append(startTime, that.startTime)
            .append(endTime, that.endTime)
            .append(description, that.description)
            .append(color, that.color)
            .append(textColor, that.textColor)
            .append(published, that.published)
            .append(pairsRequired, that.pairsRequired)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(roomId)
            .append(startTime)
            .append(endTime)
            .append(description)
            .append(color)
            .append(textColor)
            .append(published)
            .append(pairsRequired)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("roomId", roomId)
            .append("startTime", startTime)
            .append("endTime", endTime)
            .append("description", description)
            .append("color", color)
            .append("textColor", textColor)
            .append("published", textColor)
            .append("pairsRequired", pairsRequired)
            .toString();
    }

}
