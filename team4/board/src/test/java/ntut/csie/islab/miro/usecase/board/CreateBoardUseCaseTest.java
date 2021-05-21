package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.application.springboot.web.EZMiroWebMain;
import ntut.csie.islab.miro.usecase.board.createboard.CreateBoardInput;
import ntut.csie.islab.miro.usecase.board.createboard.CreateBoardUseCase;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.UUID;

@ContextConfiguration(classes= EZMiroWebMain.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class CreateBoardUseCaseTest {
    public DomainEventBus domainEventBus;

    @Autowired
    public BoardRepository boardRepository;

    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
    }

    @Test
    public void test_create_board(){
        CreateBoardUseCase createBoardUseCase= new CreateBoardUseCase(domainEventBus,boardRepository);
        CreateBoardInput input =  createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setTeamId(UUID.randomUUID());
        input.setBoardName("Board name");

        createBoardUseCase.execute(input,output);
        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
    }
}
