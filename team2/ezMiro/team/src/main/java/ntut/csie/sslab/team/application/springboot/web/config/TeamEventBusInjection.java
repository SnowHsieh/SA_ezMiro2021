package ntut.csie.sslab.team.application.springboot.web.config;


import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("TeamEventBusInjection")
public class TeamEventBusInjection {
    @Bean(name="teamEventBus")
    public DomainEventBus eventBus() {
        return new DomainEventBus();
    }
}