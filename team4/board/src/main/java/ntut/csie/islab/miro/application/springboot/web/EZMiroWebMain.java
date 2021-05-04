package ntut.csie.islab.miro.application.springboot.web;


import ntut.csie.islab.miro.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"ntut.csie.islab.miro"})
@EntityScan(basePackages = {"ntut.csie.islab.miro.adapter"})

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})

public class EZMiroWebMain extends SpringBootServletInitializer implements CommandLineRunner {
    private NotifyBoardAdapter notifyBoardAdapter;
    private DomainEventBus domainEventBus;


    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Autowired
    public void setNotifyBoardAdapter(NotifyBoardAdapter notifyBoardAdapter) { this.notifyBoardAdapter = notifyBoardAdapter; }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EZMiroWebMain.class);

    }

    public static void main(String[] args) {
        SpringApplication.run(EZMiroWebMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Team4 EZMiroWebMain run");
        domainEventBus.register(notifyBoardAdapter);
    }

}
