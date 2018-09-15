package dance.joyfrom.rest.monitoring;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.system.DiskSpaceHealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by Radosław Osiński
 */
@Component
@Endpoint(id = "readiness")
public class ReadinessEndpoint {

    private final DiskSpaceHealthIndicator diskSpaceHealthIndicator;
    private final JoyFromDataSourceHealthIndicator joyFromDataSourceHealthIndicator;

    public ReadinessEndpoint(DiskSpaceHealthIndicator diskSpaceHealthIndicator, JoyFromDataSourceHealthIndicator joyFromDataSourceHealthIndicator) {
        this.diskSpaceHealthIndicator = diskSpaceHealthIndicator;
        this.joyFromDataSourceHealthIndicator = joyFromDataSourceHealthIndicator;
    }

    @ReadOperation
    public Health health() {
        Health diskSpaceHealth = diskSpaceHealthIndicator.health();
        Health dbHealth = joyFromDataSourceHealthIndicator.health();
        if (diskSpaceHealth.getStatus() == Status.UP && dbHealth.getStatus() == Status.UP) {
            return Health.up().withDetail("disk", "OK").withDetail("db", "OK").build();
        } else {
            return Health.down().build();
        }
    }
}
