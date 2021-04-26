package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.presenter.GetBoardContentPresenter;
import ntut.csie.islab.miro.adapter.presenter.getContent.BoardContentViewModel;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.figure.adapter.repository.figure.FigureRepository;
import ntut.csie.islab.miro.usecase.board.CreateBoardUseCase;
import ntut.csie.islab.miro.usecase.board.CreateBoardUseCaseInput;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetBoardContentUseCaseTest {
    public DomainEventBus domainEventBus;
    public BoardRepository boardRepository;
    public FigureRepository figureRepository;

    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        boardRepository = new BoardRepository();
        figureRepository = new FigureRepository();
    }
    @Test
    public void test_get_board_with_empty_content_with_exist_board_id(){
        GetBoardContentUseCase getBoardContentUseCase= new GetBoardContentUseCase(domainEventBus,boardRepository,figureRepository);
        GetBoardContentUseCaseInput input =  getBoardContentUseCase.newInput();
        UUID boardId = UUID.randomUUID();
        input.setBoardId(boardId);
        GetBoardContentPresenter output = new GetBoardContentPresenter();

        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        getBoardContentUseCase.execute(input, presenter);

        assertEquals(boardId, presenter.getBoardId());
        assertEquals(0, presenter.getFigures().size());

        BoardContentViewModel boardContentViewModel = presenter.buildViewModel();

        assertEquals(boardId, boardContentViewModel.getBoardId());
        assertEquals(0, boardContentViewModel.getFigureDtos().size());

    }
}
