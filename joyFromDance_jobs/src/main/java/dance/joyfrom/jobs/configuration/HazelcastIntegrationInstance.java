//package dance.joyfrom.jobs.configuration;
//
//import com.hazelcast.config.Config;
//import com.hazelcast.config.GroupConfig;
//import com.hazelcast.config.InterfacesConfig;
//import com.hazelcast.config.NetworkConfig;
//import com.hazelcast.core.Hazelcast;
//import com.hazelcast.core.HazelcastInstance;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.core.env.Environment;
//
///**
// * Created by Radoslaw Osinski.
// */
//@Configuration
//public class HazelcastIntegrationInstance {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastIntegrationInstance.class);
//    private static final String DEFAULT_HAZELCAST_INSTANCES = "set joy.from.dance.hazelcast.interfaces";
//
//    private Environment environment;
//
//    private HazelcastConfigProviderService hazelcastConfigProviderService;
//
//    @Autowired
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Autowired
//    public void setHazelcastConfigProviderService(HazelcastConfigProviderService hazelcastConfigProviderService) {
//        this.hazelcastConfigProviderService = hazelcastConfigProviderService;
//    }
//
//    /**
//     * Gets new hazelcast instance.
//     *
//     * @return the new hazelcast instance
//     * @see "http://docs.hazelcast.org/docs/3.9/manual/html-single/index.html"
//     */
//    @Bean(name = "hazelcastInstance")
//    public HazelcastInstance getHazelcastInstance() {
//        LOGGER.info("Registering hazelcast instance in spring context as bean.");
//        final Config config = new Config();
//        config.setProperty("hazelcast.initial.min.cluster.size", "1");
//        config.setProperty("hazelcast.socket.bind.any", "false");
//        config.setProperty("hazelcast.socket.server.bind.any", "false");
//        config.setProperty("hazelcast.socket.client.bind", "false");
//        config.setProperty("hazelcast.socket.client.bind.any", "false");
//        config.setProperty("hazelcast.logging.type", "slf4j");
//        config.setGroupConfig(getGroupName());
//        config.setNetworkConfig(getNetworkConfig());
//        config.setInstanceName(getHazelcastInstanceName());
//        return Hazelcast.newHazelcastInstance(config);
//    }
//
//    private String getHazelcastInstanceName() {
//        final String hazelcastInstanceName = environment.getRequiredProperty("joy.from.dance.hazelcast.instance.name");
//        LOGGER.info("Configuring hazelcast with instance name: " + hazelcastInstanceName);
//        return hazelcastInstanceName;
//    }
//
//    private GroupConfig getGroupName() {
//        final GroupConfig groupConfig = new GroupConfig();
//        final String hazelcastGroupName = hazelcastConfigProviderService.getClusterGroupName();
//        groupConfig.setName(hazelcastGroupName);
//        groupConfig.setPassword(environment.getProperty("joy.from.dance.hazelcast.password", "set joy.from.dance.hazelcast.password"));
//        return groupConfig;
//    }
//
//    private NetworkConfig getNetworkConfig() {
//        final NetworkConfig networkConfig = new NetworkConfig();
//        networkConfig.setPortAutoIncrement(true);
//        networkConfig.setReuseAddress(true);
//        networkConfig.getJoin().getAwsConfig().setEnabled(false);
//        networkConfig.getJoin().getMulticastConfig().setEnabled(false);
//        final String[] members = environment.getProperty("joy.from.dance.hazelcast.members", "set joy.from.dance.hazelcast.members").split(",");
//        for (String member : members) {
//            networkConfig.getJoin().getTcpIpConfig().addMember(member);
//            LOGGER.info("Configuring hazelcast with tcp ip member: " + member);
//        }
//        final String hazelcastInterfaces = environment.getProperty("joy.from.dance.hazelcast.interfaces", DEFAULT_HAZELCAST_INSTANCES);
//        if (hazelcastInterfaces.isEmpty() || hazelcastInterfaces.equals(DEFAULT_HAZELCAST_INSTANCES)) {
//            LOGGER.info("Configuring hazelcast without specified interfaces");
//        } else {
//            final InterfacesConfig interfacesConfig = networkConfig.getInterfaces();
//            interfacesConfig.setEnabled(true);
//            LOGGER.info("Configuring hazelcast with specified interfaces");
//            final String[] interfaces = hazelcastInterfaces.split(",");
//            for (String hazelcastInterface : interfaces) {
//                LOGGER.info("Configuring hazelcast with interface: " + hazelcastInterface);
//                interfacesConfig.addInterface(hazelcastInterface);
//            }
//            networkConfig.setInterfaces(interfacesConfig);
//        }
//        networkConfig.getJoin().getTcpIpConfig().setEnabled(true);
//        networkConfig.getJoin().getTcpIpConfig().setConnectionTimeoutSeconds(5);
//        return networkConfig;
//    }
//
//}
