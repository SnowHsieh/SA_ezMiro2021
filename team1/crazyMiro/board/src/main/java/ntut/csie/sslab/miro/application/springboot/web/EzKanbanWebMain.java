package ntut.csie.sslab.miro.application.springboot.web;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.google.NotifyCursorAdapter;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoardSessionBroadcaster;
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
    private NotifyBoardAdapter notifyBoardAdapter;
    private NotifyCursorAdapter notifyCursorAdapter;
    private NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster;
    private BoardRepository boardRepository;


    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Autowired
    public void setNotifyBoardAdapter(NotifyBoardAdapter notifyBoardAdapter) { this.notifyBoardAdapter = notifyBoardAdapter; }

    @Autowired
    public void setNotifyCursorAdapter(NotifyCursorAdapter notifyCursorAdapter) {
        this.notifyCursorAdapter = notifyCursorAdapter;
    }

    @Autowired
    public void setNotifyBoardSessionBroadcaster(NotifyBoardSessionBroadcaster notifyBoardSessionBroadcaster) {
        this.notifyBoardSessionBroadcaster = notifyBoardSessionBroadcaster;
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
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
        domainEventBus.register(notifyCursorAdapter);
        domainEventBus.register(notifyBoardSessionBroadcaster);
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput input = createBoardUseCase.newInput();
        CqrsCommandOutput output = CqrsCommandPresenter.newInstance();
        input.setBoardId("123");
        input.setBoardName("123");
        createBoardUseCase.execute(input, output);
        System.out.println("Default board Id : " + output.getId());



    }
}
