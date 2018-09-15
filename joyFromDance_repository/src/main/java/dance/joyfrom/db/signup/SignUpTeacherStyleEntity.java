package dance.joyfrom.db.signup;

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
@NamedQuery(name = SignUpTeacherStyleEntity.DELETE, query = "DELETE FROM SignUpTeacherStyleEntity sts WHERE sts.email = :email")
@IdClass(SignUpTeacherStylePk.class)
@Table(name = "sign_up_teacher_styles")
public class SignUpTeacherStyleEntity {

    public static final String DELETE = "SignUpTeacherStyleEntity.DELETE";
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

        SignUpTeacherStyleEntity that = (SignUpTeacherStyleEntity) o;

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
