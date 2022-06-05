package ntut.csie.islab.miro.application.springboot.web;


import ntut.csie.islab.miro.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardRepositoryPeer;
import ntut.csie.islab.miro.adapter.gateway.repository.figure.line.LineRepositoryPeer;
import ntut.csie.islab.miro.adapter.gateway.repository.figure.connectablefigure.stickynote.StickyNoteRepositoryPeer;
import ntut.csie.islab.miro.usecase.eventhandler.NotifyBoardSessionBroadcaster;
import ntut.csie.sslab.ddd.model.DomainEventBus;
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

@ComponentScan(basePackages = {"ntut.csie.islab.miro", "ntut.csie.islab.account", "ntut.csie.islab.team"})
@EntityScan(basePackages = {"ntut.csie.islab.miro.adapter", "ntut.csie.islab.account.adapter"})
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})

@EnableWebSocket
public class EZMiroWebMain extends SpringBootServletInitializer implements CommandLineRunner {
    private NotifyBoardAdapter notifyBoardAdapter;
    private NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster;
    private DomainEventBus domainEventBus;
    private BoardRepositoryPeer boardRepositoryPeer;
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;
    private LineRepositoryPeer lineRepositoryPeer;


    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Autowired
    public void setNotifyBoardAdapter(NotifyBoardAdapter notifyBoardAdapter) {
        this.notifyBoardAdapter = notifyBoardAdapter;
    }

    @Autowired
    public void setNotifyBoardSessionBroadcaster(NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster) {
        this.notifyBoardSessionBroadcaster = notifyBoardSessionBroadcaster;
    }

    @Autowired
    public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer) {
        this.boardRepositoryPeer = boardRepositoryPeer;
    }
    @Autowired
    public void setStickyNoteRepositoryPeer(StickyNoteRepositoryPeer stickyNoteRepositoryPeer) {
        this.stickyNoteRepositoryPeer = stickyNoteRepositoryPeer;
    }

    @Autowired
    public void setLineRepositoryPeer(LineRepositoryPeer lineRepositoryPeer) {
        this.lineRepositoryPeer = lineRepositoryPeer;
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
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
