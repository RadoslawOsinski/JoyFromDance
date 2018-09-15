//package dance.joyfrom.jobs.configuration;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.jar.Attributes;
//import java.util.jar.JarFile;
//import java.util.jar.Manifest;
//import java.util.zip.ZipEntry;
//
///**
// * Created by Radosław Osiński
// */
//@Service
//public class HazelcastConfigProviderService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(HazelcastConfigProviderService.class);
//
//    private Environment environment;
//
//    @Autowired
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }
//
//    public String getClusterGroupName() {
//        final String hazelcastClusterGroupName = environment.getProperty("joy.from.dance.hazelcast.name", "joyFromDanceJobsHazelcastCluster") + "_buildId_" + getBuildIdFromJar();
//        LOGGER.info("Hazelcast jobs application configured cluster group name: {}", hazelcastClusterGroupName);
//        return hazelcastClusterGroupName;
//    }
//
//    private static String getBuildIdFromJar() {
//        String buildId;
//        final URL jarUrl = HazelcastConfigProviderService.class.getProtectionDomain().getCodeSource().getLocation();
//        try {
//            try (JarFile jf = new JarFile(new File(jarUrl.toURI()))) {
//                final ZipEntry entry = jf.getEntry("META-INF/MANIFEST.MF");
//                buildId = getBuildIdFromManifestStream(jf, entry);
//            }
//        } catch (IOException | URISyntaxException e) {
//            LOGGER.error("Problem with opening jar where META-INF/MANIFEST.MF exists", e);
//            buildId = "error";
//        }
//        return buildId;
//    }
//
//    private static String getBuildIdFromManifestStream(JarFile jf, ZipEntry entry) {
//        String buildId;
//        try (InputStream in = jf.getInputStream(entry)) {
//            Manifest manifest;
//            manifest = new Manifest(in);
//            Attributes attributes = manifest.getMainAttributes();
//            if (StringUtils.isEmpty("build-id")) {
//                buildId = "dev";
//            } else {
//                buildId = attributes.getValue("build-id");
//            }
//        } catch (IOException e) {
//            LOGGER.error("Problem with reading MANIFEST.MF in persistence context", e);
//            buildId = "error";
//        }
//        if (buildId.isEmpty()) {
//            buildId = "emptyBuildId";
//        }
//        return buildId;
//    }
//
//}
