package dance.joyfrom.rest.security;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.Instant;

/**
 * Created by Radosław Osiński
 */
public class Session implements Serializable {

    private final Long userId;
    private final Instant loginTime;
    private final Instant lastRequest;

    public Session(Long userId, Instant loginTime, Instant lastRequest) {
        this.userId = userId;
        this.loginTime = loginTime;
        this.lastRequest = lastRequest;
    }

    public Long getUserId() {
        return userId;
    }

    public Instant getLoginTime() {
        return loginTime;
    }

    public Instant getLastRequest() {
        return lastRequest;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("userId", userId)
            .append("loginTime", loginTime)
            .append("lastRequest", lastRequest)
            .toString();
    }

}
