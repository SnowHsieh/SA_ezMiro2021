package ntut.csie.sslab.kanban.application.springboot.web;

import ntut.csie.sslab.kanban.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.sslab.kanban.adapter.gateway.eventbus.google.NotifyWorkflowAdapter;
import ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2.BoardRepositoryPeer;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"ntut.csie.sslab.kanban"})
@EntityScan(basePackages={"ntut.csie.sslab.kanban.adapter.gateway"})

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class EzKanbanWebMain extends SpringBootServletInitializer implements CommandLineRunner {

    private DomainEventBus domainEventBus;
    private NotifyBoardAdapter notifyBoardAdapter;
    private NotifyWorkflowAdapter notifyWorkflowAdapter;
    private BoardRepositoryPeer boardRepositoryPeer;


    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Autowired
    public void setNotifyBoardAdapter(NotifyBoardAdapter notifyBoardAdapter) { this.notifyBoardAdapter = notifyBoardAdapter; }

    @Autowired
    public void setNotifyWorkflowAdapter(NotifyWorkflowAdapter notifyWorkflowAdapter) { this.notifyWorkflowAdapter = notifyWorkflowAdapter; }


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

        domainEventBus.register(notifyBoardAdapter);
        domainEventBus.register(notifyWorkflowAdapter);
    }
}
