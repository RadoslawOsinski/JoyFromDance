package dance.joyfrom.services.user;

import dance.joyfrom.db.user.Roles;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Radosław Osiński
 */
public class UserRole {

    private long userId;
    private Roles role;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("userId", userId)
            .append("role", role)
            .toString();
    }
}
