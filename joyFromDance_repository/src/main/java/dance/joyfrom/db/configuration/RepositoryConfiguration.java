package dance.joyfrom.db.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.SchemaAutoTooling;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Radoslaw Osinski.
 */
@Configuration
@EnableTransactionManagement
public class RepositoryConfiguration {

    private final DataSource joyFromDanceDataSource;
    private final Environment environment;
    private final HazelcastConfigProviderService hazelcastConfigProviderService;

    public RepositoryConfiguration(DataSource joyFromDanceDataSource, Environment environment, HazelcastConfigProviderService hazelcastConfigProviderService) {
        this.joyFromDanceDataSource = joyFromDanceDataSource;
        this.environment = environment;
        this.hazelcastConfigProviderService = hazelcastConfigProviderService;
    }

    /**
     * Configure properties for JPA instances.
     *
     * @return instance of Properties
     */
    @DependsOn({"hazelcastInstance" })
    @Bean
    public Properties getCwsfeJoyFromDanceJPAProperties() {
        final Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect"));
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto", SchemaAutoTooling.NONE.name().toLowerCase()));
        properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql", "false"));
        properties.setProperty("hibernate.format_sql", environment.getProperty("hibernate.format_sql", "false"));
        properties.setProperty("hibernate.archive.autodetection", environment.getProperty("hibernate.archive.autodetection", "true"));
        properties.setProperty("hibernate.id.new_generator_mappings", Boolean.FALSE.toString());

        properties.setProperty("hibernate.cache.use_second_level_cache", environment.getProperty("hibernate.cache.use_second_level_cache", Boolean.TRUE.toString()));
        properties.setProperty("hibernate.cache.use_query_cache", environment.getProperty("hibernate.cache.use_query_cache", Boolean.TRUE.toString()));

        properties.setProperty("hibernate.cache.region.factory_class", "com.hazelcast.hibernate.HazelcastCacheRegionFactory");
        properties.setProperty("hibernate.generate_statistics", environment.getProperty("hibernate.generate_statistics", Boolean.FALSE.toString()));
        properties.setProperty("hibernate.cache.hazelcast.use_lite_member", Boolean.TRUE.toString());
        properties.setProperty("hibernate.cache.use_minimal_puts", Boolean.TRUE.toString());
        properties.setProperty("hibernate.cache.hazelcast.use_native_client", Boolean.FALSE.toString());
        //todo hazelcast conf for kubernetes auto discovery
//        properties.setProperty("hibernate.cache.hazelcast.instance_name", environment.getProperty("joy.from.dance.hazelcast.instance.name", "joyFromDanceHazelcastInstance1"));
//        properties.setProperty("hibernate.cache.hazelcast.native_client_address", environment.getProperty("joy.from.dance.hazelcast.members", "set joy.from.dance.hazelcast.members"));
        properties.setProperty("hibernate.cache.hazelcast.native_client_address", "127.0.0.1:5701");
        properties.setProperty("hibernate.cache.hazelcast.native_client_group", hazelcastConfigProviderService.getClusterGroupName());
        properties.setProperty("hibernate.cache.hazelcast.native_client_password", environment.getProperty("joy.from.dance.hazelcast.password", "changeMyValueOnProductionEnvironment"));

        return properties;
    }

    /**
     * Gets annotation session factory bean.
     *
     * @return the annotation session factory bean
     */
    @Bean(name = "sessionFactory")
    @DependsOn({"dbMigrationManager", "hazelcastInstance" })
    public LocalSessionFactoryBean getAnnotationSessionFactoryBean() {
        final LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setPackagesToScan("dance.joyfrom.db", "dance.joyfrom.repository");
        localSessionFactoryBean.setDataSource(joyFromDanceDataSource);
        localSessionFactoryBean.setHibernateProperties(getCwsfeJoyFromDanceJPAProperties());
        return localSessionFactoryBean;
    }

    @Bean(name = "transactionManager")
    @DependsOn("sessionFactory")
    public PlatformTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        final HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory);
        return hibernateTransactionManager;
    }

}
