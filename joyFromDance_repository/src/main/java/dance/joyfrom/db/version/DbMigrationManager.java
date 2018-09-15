package dance.joyfrom.db.version;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Automatically updates database
 * <p>
 * Created by Radosław Osiński
 */
@Component
public class DbMigrationManager implements InitializingBean {

    private final DataSource joyFromDanceDataSource;

    public DbMigrationManager(DataSource joyFromDanceDataSource) {
        this.joyFromDanceDataSource = joyFromDanceDataSource;
    }

    private void updateJoyFromDanceDatabaseSchema() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(joyFromDanceDataSource);
        flyway.migrate();
        flyway.setBaselineOnMigrate(true);
        flyway.repair();
    }

    @Override
    public void afterPropertiesSet() {
        updateJoyFromDanceDatabaseSchema();
    }
}
