package ntut.csie.team5.application.springboot.web;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.team5.usecase.eventhandler.NotifyBoard;
import ntut.csie.team5.usecase.eventhandler.NotifyBoardSessionBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"ntut.csie.team5"})
@EntityScan(basePackages = {"ntut.csie.team5.adapter"})

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class EzMiroWebMain extends SpringBootServletInitializer implements CommandLineRunner {

    private DomainEventBus domainEventBus;
    private NotifyBoard notifyBoard;
    private NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster;

    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Autowired
    public void setNotifyBoard(NotifyBoard notifyBoard) {
        this.notifyBoard = notifyBoard;
    }

    @Autowired
    public void setNotifyBoardSessionBroadcaster(NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster) {
        this.notifyBoardSessionBroadcaster = notifyBoardSessionBroadcaster;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EzMiroWebMain.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(EzMiroWebMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("EzMiroWebMain run");
        domainEventBus.register(notifyBoard);
        domainEventBus.register(notifyBoardSessionBroadcaster);
    }
}
