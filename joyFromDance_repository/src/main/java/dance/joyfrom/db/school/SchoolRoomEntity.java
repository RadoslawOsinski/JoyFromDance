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
@Table(name = "school_rooms")
public class SchoolRoomEntity {

    private long id;
    private long schoolId;
    private String name;
    private SchoolEntity schoolEntity;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_rooms_s")
    @GenericGenerator(name = "school_rooms_s",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "optimizer", value = "pooled-lo"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "school_rooms_s"),
        }
    )
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "school_id", nullable = false)
    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false, insertable = false, updatable = false)
    public SchoolEntity getSchoolEntity() {
        return schoolEntity;
    }

    public void setSchoolEntity(SchoolEntity schoolEntity) {
        this.schoolEntity = schoolEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SchoolRoomEntity that = (SchoolRoomEntity) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(schoolId, that.schoolId)
            .append(name, that.name)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(schoolId)
            .append(name)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("schoolId", schoolId)
            .append("name", name)
            .toString();
    }
}
