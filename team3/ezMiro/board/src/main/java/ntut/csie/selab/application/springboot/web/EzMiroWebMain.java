package ntut.csie.selab.application.springboot.web;
import ntut.csie.selab.adapter.websocket.BoardWebSocketController;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.eventHandler.NotifyLine;
import ntut.csie.selab.usecase.eventHandler.NotifyUsersInBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


@SpringBootApplication
@EnableWebSocket
@ComponentScan(basePackages = {"ntut.csie.selab"} )
public class EzMiroWebMain implements CommandLineRunner {
    private DomainEventBus domainEventBus;
    private NotifyBoard notifyBoard;
    private NotifyUsersInBoard notifyUsersInBoard;
    private NotifyLine notifyLine;

    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Autowired
    public void setNotifyBoard(NotifyBoard notifyBoard, NotifyUsersInBoard notifyUsersInBoard, NotifyLine notifyLine) {
        this.notifyBoard = notifyBoard;
        this.notifyUsersInBoard = notifyUsersInBoard;
        this.notifyLine = notifyLine;
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(EzMiroWebMain.class) ;
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        BoardWebSocketController.setApplicationContext(configurableApplicationContext);
    }

    @Override
    public void run(String... arg0) throws Exception {
        System.out.println("EzMiroWebMain run");

        domainEventBus.register(notifyBoard);
        domainEventBus.register(notifyUsersInBoard);
        domainEventBus.register(notifyLine);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
