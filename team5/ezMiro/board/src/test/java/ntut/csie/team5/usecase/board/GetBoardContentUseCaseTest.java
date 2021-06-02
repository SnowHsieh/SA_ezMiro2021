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

        String firstNoteId = postNote(boardId, defaultLeftTopPositionX, defaultLeftTopPositionY, defaultHeight, defaultWidth, "#ff0000");
        String firstLineId = drawLine(boardId, defaultEndpointA, defaultEndpointB);

        GetBoardContentUseCase getBoardContentUseCase = new GetBoardContentUseCaseImpl(boardRepository, noteRepository, lineRepository, domainEventBus);
        GetBoardContentInput getBoardContentInput = (GetBoardContentInput) getBoardContentUseCase;
        GetBoardContentPresenter getBoardContentOutput = new GetBoardContentPresenter();

        getBoardContentInput.setBoardId(boardId);

        getBoardContentUseCase.execute(getBoardContentInput, getBoardContentOutput);

        BoardContentViewModel boardContentViewModel = getBoardContentOutput.buildViewModel();

        assertEquals(boardId, boardContentViewModel.getBoardId());
        assertEquals(2, boardContentViewModel.getFigureDtos().size());

        FigureDto figureDto = boardContentViewModel.getFigureDtos().get(0);
        assertEquals(firstNoteId, figureDto.getFigureId());
        figureDto = boardContentViewModel.getFigureDtos().get(1);
        assertEquals(firstLineId, figureDto.getFigureId());
    }
}
