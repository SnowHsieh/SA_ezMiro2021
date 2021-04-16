package ntut.csie.sslab.miro.application.springboot.web;

import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.usecase.eventhandler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"ntut.csie.sslab.miro"})
@EntityScan(basePackages={"ntut.csie.sslab.miro.adapter.gateway"})

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class EzKanbanWebMain extends SpringBootServletInitializer implements CommandLineRunner {

    private DomainEventBus domainEventBus;
    private NotifyBoard notifyBoard;
    private NotifyWorkflow notifyWorkflow;
    private BoardRepositoryPeer boardRepositoryPeer;


    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Autowired
    public void setNotifyBoard(NotifyBoard notifyBoard) { this.notifyBoard = notifyBoard; }

    @Autowired
    public void setNotifyWorkflow(NotifyWorkflow notifyWorkflow) { this.notifyWorkflow = notifyWorkflow; }


    @Autowired
    public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer) {
        this.boardRepositoryPeer = boardRepositoryPeer;
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EzKanbanWebMain.class);
    }

    public static void main(String[] args){
        SpringApplication.run(EzKanbanWebMain.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        System.out.println("EzKanbanWebMain run");

        domainEventBus.register(notifyBoard);
        domainEventBus.register(notifyWorkflow);
    }
}
