package ntut.csie.sslab.miro.application.springboot.web;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.NotifyBoardAdapter;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@ComponentScan(basePackages = {"ntut.csie.sslab.miro"})
@EntityScan(basePackages={"ntut.csie.sslab.miro.adapter.gateway"})
@EnableWebSocket
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class CanvasMain extends SpringBootServletInitializer implements CommandLineRunner {

    private DomainEventBus domainEventBus;
    private BoardRepository boardRepository;
    private FigureRepository figureRepository;
    private NotifyBoardAdapter notifyBoardAdapter;

    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setFigureRepository(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    @Autowired
    public void setNotifyBoardAdapter(NotifyBoardAdapter notifyBoardAdapter) {
        this.notifyBoardAdapter = notifyBoardAdapter;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CanvasMain.class);
    }

    public static void main(String[] args){
        SpringApplication.run(CanvasMain.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        System.out.println("CanvasMain run");
        domainEventBus.register(notifyBoardAdapter);
    }
}
