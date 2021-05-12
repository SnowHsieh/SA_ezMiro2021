package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.ZOrderType;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureZOrderInput;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureZOrderUseCase;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureZOrderUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ChangeFigureZOrderUseCaseTest extends AbstractTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        domainEventBus.register(notifyBoard);
    }

    @Test
    public void should_success_when_bring_figure_forward() {
        ChangeFigureZOrderUseCase changeFigureZOrderUseCase = new ChangeFigureZOrderUseCaseImpl(boardRepository, domainEventBus);
        ChangeFigureZOrderInput changeFigureZOrderInput = changeFigureZOrderUseCase.newInput();
        CqrsCommandPresenter changeFigureOrderOutput = CqrsCommandPresenter.newInstance();

        String boardId = createBoard(projectId, boardName);
        String firstNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String secondNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);

        changeFigureZOrderInput.setBoardId(boardId);
        changeFigureZOrderInput.setFigureId(firstNoteId);
        changeFigureZOrderInput.setZOrderType(ZOrderType.BRING_FORWARD);

        Board board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getCommittedFigures().get(0).getZOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getZOrder());

        changeFigureZOrderUseCase.execute(changeFigureZOrderInput, changeFigureOrderOutput);
        assertNotNull(changeFigureOrderOutput.getId());
        assertEquals(ExitCode.SUCCESS, changeFigureOrderOutput.getExitCode());

        board = boardRepository.findById(changeFigureOrderOutput.getId()).get();
        assertEquals(0, board.getCommittedFigures().get(0).getZOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getZOrder());
    }

    @Test
    public void should_success_when_send_figure_backwards() {
        ChangeFigureZOrderUseCase changeFigureZOrderUseCase = new ChangeFigureZOrderUseCaseImpl(boardRepository, domainEventBus);
        ChangeFigureZOrderInput changeFigureZOrderInput = changeFigureZOrderUseCase.newInput();
        CqrsCommandPresenter changeFigureOrderOutput = CqrsCommandPresenter.newInstance();

        String boardId = createBoard(projectId, boardName);
        String firstNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String secondNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);

        changeFigureZOrderInput.setBoardId(boardId);
        changeFigureZOrderInput.setFigureId(secondNoteId);
        changeFigureZOrderInput.setZOrderType(ZOrderType.SEND_BACKWARDS);

        Board board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getCommittedFigures().get(0).getZOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getZOrder());

        changeFigureZOrderUseCase.execute(changeFigureZOrderInput, changeFigureOrderOutput);
        assertNotNull(changeFigureOrderOutput.getId());
        assertEquals(ExitCode.SUCCESS, changeFigureOrderOutput.getExitCode());

        board = boardRepository.findById(changeFigureOrderOutput.getId()).get();
        assertEquals(0, board.getCommittedFigures().get(0).getZOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getZOrder());
    }

    @Test
    public void should_success_when_bring_figure_to_front() {
        ChangeFigureZOrderUseCase changeFigureZOrderUseCase = new ChangeFigureZOrderUseCaseImpl(boardRepository, domainEventBus);
        ChangeFigureZOrderInput changeFigureZOrderInput = changeFigureZOrderUseCase.newInput();
        CqrsCommandPresenter changeFigureOrderOutput = CqrsCommandPresenter.newInstance();

        String boardId = createBoard(projectId, boardName);
        String firstNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String secondNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String thirdNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);

        changeFigureZOrderInput.setBoardId(boardId);
        changeFigureZOrderInput.setFigureId(firstNoteId);
        changeFigureZOrderInput.setZOrderType(ZOrderType.BRING_TO_FRONT);

        Board board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getCommittedFigures().get(0).getZOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getZOrder());
        assertEquals(2, board.getCommittedFigures().get(2).getZOrder());

        changeFigureZOrderUseCase.execute(changeFigureZOrderInput, changeFigureOrderOutput);
        assertNotNull(changeFigureOrderOutput.getId());
        assertEquals(ExitCode.SUCCESS, changeFigureOrderOutput.getExitCode());

        board = boardRepository.findById(changeFigureOrderOutput.getId()).get();
        assertEquals(0, board.getCommittedFigures().get(0).getZOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getZOrder());
        assertEquals(2, board.getCommittedFigures().get(2).getZOrder());
    }

    @Test
    public void should_success_when_send_figure_to_back() {
        ChangeFigureZOrderUseCase changeFigureZOrderUseCase = new ChangeFigureZOrderUseCaseImpl(boardRepository, domainEventBus);
        ChangeFigureZOrderInput changeFigureZOrderInput = changeFigureZOrderUseCase.newInput();
        CqrsCommandPresenter changeFigureOrderOutput = CqrsCommandPresenter.newInstance();

        String boardId = createBoard(projectId, boardName);
        String firstNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String secondNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String thirdNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);

        changeFigureZOrderInput.setBoardId(boardId);
        changeFigureZOrderInput.setFigureId(thirdNoteId);
        changeFigureZOrderInput.setZOrderType(ZOrderType.SEND_TO_BACK);

        Board board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getCommittedFigures().get(0).getZOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getZOrder());
        assertEquals(2, board.getCommittedFigures().get(2).getZOrder());

        changeFigureZOrderUseCase.execute(changeFigureZOrderInput, changeFigureOrderOutput);
        assertNotNull(changeFigureOrderOutput.getId());
        assertEquals(ExitCode.SUCCESS, changeFigureOrderOutput.getExitCode());

        board = boardRepository.findById(changeFigureOrderOutput.getId()).get();
        assertEquals(0, board.getCommittedFigures().get(0).getZOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getZOrder());
        assertEquals(2, board.getCommittedFigures().get(2).getZOrder());
    }
}
