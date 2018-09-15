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
@NamedQuery(name = SchoolEntity.IS_SCHOOL_OWNER, query = "select count(s) FROM SchoolEntity s WHERE s.id = :schoolId and s.ownerEmail = :ownerEmail")
@NamedQuery(name = SchoolEntity.DELETE_BY_ID, query = "delete FROM SchoolEntity s WHERE s.id = :id")
@Table(name = "schools")
public class SchoolEntity {

    public static final String IS_SCHOOL_OWNER = "SchoolEntity.IS_SCHOOL_OWNER";
    public static final String DELETE_BY_ID = "SchoolEntity.DELETE_BY_ID";

    private long id;
    private String name;
    private String ownerEmail;
    private ZonedDateTime created;
    private List<SchoolAdminEntity> schoolAdmins;
    private List<SchoolRoomEntity> schoolRooms;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schools_s")
    @GenericGenerator(name = "schools_s",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "optimizer", value = "pooled-lo"),
            @Parameter(name = "increment_size", value = "1"),
            @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = "schools_s"),
        }
    )
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "owner_email", nullable = false, length = 250)
    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "school")
    public List<SchoolAdminEntity> getSchoolAdmins() {
        return schoolAdmins;
    }

    public void setSchoolAdmins(List<SchoolAdminEntity> schoolAdmins) {
        this.schoolAdmins = schoolAdmins;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolEntity")
    public List<SchoolRoomEntity> getSchoolRooms() {
        return schoolRooms;
    }

    public void setSchoolRooms(List<SchoolRoomEntity> schoolRooms) {
        this.schoolRooms = schoolRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SchoolEntity that = (SchoolEntity) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(name, that.name)
            .append(ownerEmail, that.ownerEmail)
            .append(created, that.created)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(ownerEmail)
            .append(created)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("name", name)
            .append("ownerEmail", ownerEmail)
            .append("created", created)
            .toString();
    }
}
