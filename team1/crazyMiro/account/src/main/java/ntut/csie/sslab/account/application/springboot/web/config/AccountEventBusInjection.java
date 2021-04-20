package ntut.csie.sslab.account.application.springboot.web.config;


import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("AccountEventBusInjection")
public class AccountEventBusInjection {

    @Bean(name="accountEventBus")
    public DomainEventBus eventBus() {
        return new GoogleEventBus();
    }

}