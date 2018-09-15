package dance.joyfrom.db.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Created by Radoslaw Osinski.
 */
@Configuration
public class DataSourceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfiguration.class);

    private final Environment environment;

    public DataSourceConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "joyFromDanceDataSource")
    public DataSource getTomcatJndiDataSource() {
        LOGGER.info("Attaching to hikari data source");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getProperty("joy.from.dance.data.source.driverClassName", "org.postgresql.Driver"));
        String jdbcUrl = environment.getProperty("joy.from.dance.data.source.url", "set joy.from.dance.data.source.url property");
        String username = environment.getProperty("joy.from.dance.data.source.username", "set joy.from.dance.data.source.username property");
        String password = environment.getProperty("joy.from.dance.data.source.password", "set joy.from.dance.data.source.password property");
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        LOGGER.trace("Attaching to hikari data source. Url: '{}', user: '{}', password: '{}'", jdbcUrl, username, password);
        return dataSource;
    }

}
