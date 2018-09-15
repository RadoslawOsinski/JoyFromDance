//package dance.joyfrom.website.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.apache.catalina.LifecycleListener;
//import org.apache.catalina.core.AprLifecycleListener;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by Radosław Osiński
// */
//@Configuration
//public class AprConfiguration {
//    @Value("${server.tomcat.apr.enabled:false}")
//    private boolean enabled;
//
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        TomcatEmbeddedServletContainerFactory container = new TomcatEmbeddedServletContainerFactory();
//        if (enabled) {
//            LifecycleListener arpLifecycle = new AprLifecycleListener();
//            container.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
//            container.addContextLifecycleListeners(arpLifecycle);
//        }
//
//        return container;
//    }
//}
