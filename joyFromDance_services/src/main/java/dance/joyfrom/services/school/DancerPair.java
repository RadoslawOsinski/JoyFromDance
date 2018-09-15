package dance.joyfrom.services.school;

import dance.joyfrom.db.school.DancerPairStatus;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Radosław Osiński
 */
public class DancerPair {

    private Long id;
    private String email1;
    private String email2;
    private DancerPairStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public DancerPairStatus getStatus() {
        return status;
    }

    public void setStatus(DancerPairStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("email1", email1)
            .append("email2", email2)
            .append("status", status)
            .toString();
    }
}
