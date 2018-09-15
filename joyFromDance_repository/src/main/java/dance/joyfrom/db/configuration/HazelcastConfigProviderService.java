package dance.joyfrom.db.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Created by Radosław Osiński
 */
@Service
public class HazelcastConfigProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastConfigProviderService.class);

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    String getClusterGroupName() {
        final String hazelcastClusterGroupName =
            environment.getProperty("joy.from.dance.hazelcast.name", "joyFromDanceHazelcastCluster") +
                "_buildId_" +
                environment.getProperty("build-id", "dev");
        LOGGER.info("Hazelcast configured cluster group name: {}", hazelcastClusterGroupName);
        return hazelcastClusterGroupName;
    }

}
