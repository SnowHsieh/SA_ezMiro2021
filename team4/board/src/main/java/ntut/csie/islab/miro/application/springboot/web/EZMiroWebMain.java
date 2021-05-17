package ntut.csie.islab.miro.application.springboot.web;


import ntut.csie.islab.miro.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.usecase.board.CreateBoardInput;
import ntut.csie.islab.miro.usecase.board.CreateBoardUseCase;
import ntut.csie.islab.miro.usecase.eventHandler.NotifyBoardSessionBroadcaster;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.UUID;

@ComponentScan(basePackages = {"ntut.csie.islab.miro"})
@EntityScan(basePackages = {"ntut.csie.islab.miro.adapter"})
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})

@EnableWebSocket
public class EZMiroWebMain extends SpringBootServletInitializer implements CommandLineRunner {
    private NotifyBoardAdapter notifyBoardAdapter;
    private NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster;
    private DomainEventBus domainEventBus;
    private BoardRepository boardRepository;

    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Autowired
    public void setNotifyBoardAdapter(NotifyBoardAdapter notifyBoardAdapter) { this.notifyBoardAdapter = notifyBoardAdapter; }

    @Autowired
    public void setNotifyBoardSessionBroadcaster(NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster) {
        this.notifyBoardSessionBroadcaster = notifyBoardSessionBroadcaster;
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

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
        domainEventBus.register(notifyBoardSessionBroadcaster);
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(domainEventBus,boardRepository);
        CreateBoardInput input = createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setTeamId(UUID.randomUUID());
        input.setBoardName("EZMIROISLAB");
        createBoardUseCase.execute(input, output);
        System.out.println("Default board Id : " + output.getId());

    }
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
