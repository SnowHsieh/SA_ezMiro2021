package ntut.csie.islab.miro.application.springboot.web.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EndpointInjection {
    @Bean
    public EndpointConfigure newConfigure()
    {
        return new EndpointConfigure();
    }
}
