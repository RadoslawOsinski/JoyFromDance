package dance.joyfrom.db.signup;

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
@NamedQuery(name = SignUpDanceFloorOwnerEntity.DELETE, query = "DELETE FROM SignUpDanceFloorOwnerEntity sdfo WHERE sdfo.email = :email")
@NamedQuery(name = SignUpDanceFloorOwnerEntity.GET_BY_EMAIL, query = "select sdfo FROM SignUpDanceFloorOwnerEntity sdfo WHERE sdfo.email = :email")
@Table(name = "sign_up_dance_floor_owner")
public class SignUpDanceFloorOwnerEntity {

    public static final String DELETE = "SignUpDanceFloorOwnerEntity.DELETE";
    public static final String GET_BY_EMAIL = "SignUpDanceFloorOwnerEntity.GET_BY_EMAIL";
    private String email;
    private Double lat;
    private Double lng;

    @Id
    @Basic
    @Column(name = "email", nullable = false, length = 256)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "lat", nullable = false)
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Basic
    @Column(name = "lng", nullable = false)
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SignUpDanceFloorOwnerEntity that = (SignUpDanceFloorOwnerEntity) o;

        return new EqualsBuilder()
            .append(email, that.email)
            .append(lat, that.lat)
            .append(lng, that.lng)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(email)
            .append(lat)
            .append(lng)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("email", email)
            .append("lat", lat)
            .append("lng", lng)
            .toString();
    }
}
