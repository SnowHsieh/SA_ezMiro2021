package ntut.csie.team5.usecase.board;

import ntut.csie.team5.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.team5.adapter.presenter.board.getcontent.GetBoardContentPresenter;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentInput;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentUseCase;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentUseCaseImpl;
import ntut.csie.team5.usecase.figure.FigureDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetBoardContentUseCaseTest extends AbstractTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        domainEventBus.register(notifyBoard);
    }

    @Test
    public void should_succeed_when_get_board_content_with_existing_board_id() {

        String boardId = createBoard(projectName, boardName);

        String firstNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, "#ff0000");

        GetBoardContentUseCase useCase = new GetBoardContentUseCaseImpl(boardRepository,figureRepository, domainEventBus);
        GetBoardContentInput input = (GetBoardContentInput) useCase;
        input.setBoardId(boardId);

        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        useCase.execute(input, presenter);

        BoardContentViewModel boardContentViewModel = presenter.buildViewModel();

        assertEquals(boardId, boardContentViewModel.getBoardId());
        assertEquals(1, boardContentViewModel.getFigureDtos().size());

        FigureDto figureDto = boardContentViewModel.getFigureDtos().get(0);
        assertEquals(firstNoteId, figureDto.getFigureId());
    }

}
