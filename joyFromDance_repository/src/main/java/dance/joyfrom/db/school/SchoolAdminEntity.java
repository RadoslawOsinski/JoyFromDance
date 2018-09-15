package dance.joyfrom.db.school;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;


/**
 * Created by Radosław Osiński
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = SchoolAdminEntity.DELETE, query = "DELETE FROM SchoolAdminEntity sa WHERE sa.schoolId = :schoolId and email = :email")
@NamedQuery(name = SchoolAdminEntity.DELETE_BY_SCHOOL_ID, query = "DELETE FROM SchoolAdminEntity sa WHERE sa.schoolId = :schoolId")
@NamedQuery(name = SchoolAdminEntity.GET_ADMIN_SCHOOLS, query = "select sa FROM SchoolAdminEntity sa WHERE email = :email")
@IdClass(SchoolAdminPk.class)
@Table(name = "school_admins")
public class SchoolAdminEntity {

    public static final String DELETE = "SchoolAdminEntity.DELETE";
    public static final String GET_ADMIN_SCHOOLS = "SchoolAdminEntity.GET_ADMIN_SCHOOLS";
    public static final String DELETE_BY_SCHOOL_ID = "SchoolAdminEntity.DELETE_BY_SCHOOL_ID";
    private long schoolId;
    private String email;
    private SchoolEntity school;

    @Id
    @Basic
    @Column(name = "school_id", nullable = false)
    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long id) {
        this.schoolId = id;
    }

    @Id
    @Basic
    @Column(name = "email", nullable = false, length = 250)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false, insertable = false, updatable = false)
    public SchoolEntity getSchool() {
        return school;
    }

    public void setSchool(SchoolEntity school) {
        this.school = school;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SchoolAdminEntity that = (SchoolAdminEntity) o;

        return new EqualsBuilder()
            .append(schoolId, that.schoolId)
            .append(email, that.email)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(schoolId)
            .append(email)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", schoolId)
            .append("email", email)
            .toString();
    }
}
