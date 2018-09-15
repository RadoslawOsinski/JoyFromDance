package dance.joyfrom.db.dancer;

import dance.joyfrom.db.school.SchoolEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by Radosław Osiński
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = DancerSchoolEntity.DELETE, query = "DELETE FROM DancerSchoolEntity st WHERE st.schoolId = :schoolId and email = :email")
@IdClass(DancerSchoolPk.class)
@Table(name = "school_teachers")
public class DancerSchoolEntity {

    public static final String DELETE = "DancerSchoolEntity.DELETE";
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

        DancerSchoolEntity that = (DancerSchoolEntity) o;

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
