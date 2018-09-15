package dance.joyfrom.rest.monitoring;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Radosław Osiński
 */
class LoadBalancerHealthCheckRestControllerTest {

    @Test
    void healthCheck() {
        //given
        String skipPatternConfiguration = "(^cleanup.*|.+favicon.*|/)";
        String someLoadbalancerUrl = "/";

        //when
        boolean shouldSkip = Pattern.compile(skipPatternConfiguration).matcher(someLoadbalancerUrl).matches();

        //then
        assertTrue(shouldSkip, "Load balancer requests should not be traced. It is expensive!");
    }
}
