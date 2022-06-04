package account;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})

@EnableWebSocket
public class EZMiroAccountMain extends SpringBootServletInitializer implements CommandLineRunner {
//    private NotifyBoardAdapter notifyBoardAdapter;
//    private NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster;
//    private DomainEventBus domainEventBus;
//    private BoardRepositoryPeer boardRepositoryPeer;
//    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;
//    private LineRepositoryPeer lineRepositoryPeer;
//
//
//    @Autowired
//    public void setDomainEventBus(DomainEventBus domainEventBus) {
//        this.domainEventBus = domainEventBus;
//    }
//
//    @Autowired
//    public void setNotifyBoardAdapter(NotifyBoardAdapter notifyBoardAdapter) {
//        this.notifyBoardAdapter = notifyBoardAdapter;
//    }
//
//    @Autowired
//    public void setNotifyBoardSessionBroadcaster(NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster) {
//        this.notifyBoardSessionBroadcaster = notifyBoardSessionBroadcaster;
//    }
//
//    @Autowired
//    public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer) {
//        this.boardRepositoryPeer = boardRepositoryPeer;
//    }
//    @Autowired
//    public void setStickyNoteRepositoryPeer(StickyNoteRepositoryPeer stickyNoteRepositoryPeer) {
//        this.stickyNoteRepositoryPeer = stickyNoteRepositoryPeer;
//    }
//
//    @Autowired
//    public void setLineRepositoryPeer(LineRepositoryPeer lineRepositoryPeer) {
//        this.lineRepositoryPeer = lineRepositoryPeer;
//    }
//
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(EZMiroWebMain.class);
//
//    }

    public static void main(String[] args) {
        SpringApplication.run(EZMiroAccountMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Team4 EZMiroAccountMain run");
//        domainEventBus.register(notifyBoardAdapter);
//        domainEventBus.register(notifyBoardSessionBroadcaster);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
