package dance.joyfrom.db.signup;

import dance.joyfrom.db.school.SchoolAdminPk;
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
@NamedQuery(name = SignUpDancerStyleEntity.DELETE, query = "DELETE FROM SignUpDancerStyleEntity sds WHERE sds.email = :email")
@IdClass(SignUpDancerStylePk.class)
@Table(name = "sign_up_dancer_styles")
public class SignUpDancerStyleEntity {

    public static final String DELETE = "SignUpDancerStyleEntity.DELETE";
    private String email;
    private String style;

    @Id
    @Basic
    @Column(name = "email", nullable = false, length = 256)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Id
    @Basic
    @Column(name = "style", nullable = false)
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SignUpDancerStyleEntity that = (SignUpDancerStyleEntity) o;

        return new EqualsBuilder()
            .append(email, that.email)
            .append(style, that.style)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(email)
            .append(style)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("email", email)
            .append("style", style)
            .toString();
    }
}
