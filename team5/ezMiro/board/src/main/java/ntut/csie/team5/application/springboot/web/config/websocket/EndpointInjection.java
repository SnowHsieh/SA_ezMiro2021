package ntut.csie.team5.application.springboot.web.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointInjection {

    @Bean
    public EndPointConfiguration newConfigure() {
        return new EndPointConfiguration();
    }
}
