package dance.joyfrom.rest.monitoring;

import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by Radosław Osiński
 */
@Component
public class JoyFromDataSourceHealthIndicator extends DataSourceHealthIndicator {

    public JoyFromDataSourceHealthIndicator(DataSource joyFromDanceDataSource) {
        super(joyFromDanceDataSource);
    }
}
