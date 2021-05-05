package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.OrderType;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureOrderInput;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureOrderUseCase;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureOrderUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ChangeFigureOrderUseCaseTest extends AbstractTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        domainEventBus.register(notifyBoard);
    }

    @Test
    public void should_success_when_figure_order_moved_front() {
        ChangeFigureOrderUseCase changeFigureOrderUseCase = new ChangeFigureOrderUseCaseImpl(boardRepository, domainEventBus);
        ChangeFigureOrderInput changeFigureOrderInput = changeFigureOrderUseCase.newInput();
        CqrsCommandPresenter changeFigureOrderOutput = CqrsCommandPresenter.newInstance();

        String boardId = createBoard(projectId, boardName);
        String firstNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String secondNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);

        changeFigureOrderInput.setBoardId(boardId);
        changeFigureOrderInput.setFigureId(firstNoteId);
        changeFigureOrderInput.setOrderType(OrderType.FRONT);

        Board board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getCommittedFigures().get(0).getOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getOrder());

        changeFigureOrderUseCase.execute(changeFigureOrderInput, changeFigureOrderOutput);
        assertNotNull(changeFigureOrderOutput.getId());
        assertEquals(ExitCode.SUCCESS, changeFigureOrderOutput.getExitCode());

        board = boardRepository.findById(changeFigureOrderOutput.getId()).get();
        assertEquals(0, board.getCommittedFigures().get(0).getOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getOrder());
    }

    @Test
    public void should_success_when_figure_order_moved_back() {
        ChangeFigureOrderUseCase changeFigureOrderUseCase = new ChangeFigureOrderUseCaseImpl(boardRepository, domainEventBus);
        ChangeFigureOrderInput changeFigureOrderInput = changeFigureOrderUseCase.newInput();
        CqrsCommandPresenter changeFigureOrderOutput = CqrsCommandPresenter.newInstance();

        String boardId = createBoard(projectId, boardName);
        String firstNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String secondNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);

        changeFigureOrderInput.setBoardId(boardId);
        changeFigureOrderInput.setFigureId(secondNoteId);
        changeFigureOrderInput.setOrderType(OrderType.BACK);

        Board board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getCommittedFigures().get(0).getOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getOrder());

        changeFigureOrderUseCase.execute(changeFigureOrderInput, changeFigureOrderOutput);
        assertNotNull(changeFigureOrderOutput.getId());
        assertEquals(ExitCode.SUCCESS, changeFigureOrderOutput.getExitCode());

        board = boardRepository.findById(changeFigureOrderOutput.getId()).get();
        assertEquals(0, board.getCommittedFigures().get(0).getOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getOrder());
    }

    @Test
    public void should_success_when_figure_order_moved_front_end() {
        ChangeFigureOrderUseCase changeFigureOrderUseCase = new ChangeFigureOrderUseCaseImpl(boardRepository, domainEventBus);
        ChangeFigureOrderInput changeFigureOrderInput = changeFigureOrderUseCase.newInput();
        CqrsCommandPresenter changeFigureOrderOutput = CqrsCommandPresenter.newInstance();

        String boardId = createBoard(projectId, boardName);
        String firstNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String secondNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String thirdNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);

        changeFigureOrderInput.setBoardId(boardId);
        changeFigureOrderInput.setFigureId(firstNoteId);
        changeFigureOrderInput.setOrderType(OrderType.FRONT_END);

        Board board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getCommittedFigures().get(0).getOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getOrder());
        assertEquals(2, board.getCommittedFigures().get(2).getOrder());

        changeFigureOrderUseCase.execute(changeFigureOrderInput, changeFigureOrderOutput);
        assertNotNull(changeFigureOrderOutput.getId());
        assertEquals(ExitCode.SUCCESS, changeFigureOrderOutput.getExitCode());

        board = boardRepository.findById(changeFigureOrderOutput.getId()).get();
        assertEquals(0, board.getCommittedFigures().get(0).getOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getOrder());
        assertEquals(2, board.getCommittedFigures().get(2).getOrder());
    }

    @Test
    public void should_success_when_figure_order_moved_back_end() {
        ChangeFigureOrderUseCase changeFigureOrderUseCase = new ChangeFigureOrderUseCaseImpl(boardRepository, domainEventBus);
        ChangeFigureOrderInput changeFigureOrderInput = changeFigureOrderUseCase.newInput();
        CqrsCommandPresenter changeFigureOrderOutput = CqrsCommandPresenter.newInstance();

        String boardId = createBoard(projectId, boardName);
        String firstNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String secondNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);
        String thirdNoteId = postNote(boardId, defaultLeftTopPosition, defaultHeight, defaultWidth, defaultColor);

        changeFigureOrderInput.setBoardId(boardId);
        changeFigureOrderInput.setFigureId(thirdNoteId);
        changeFigureOrderInput.setOrderType(OrderType.BACK_END);

        Board board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getCommittedFigures().get(0).getOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getOrder());
        assertEquals(2, board.getCommittedFigures().get(2).getOrder());

        changeFigureOrderUseCase.execute(changeFigureOrderInput, changeFigureOrderOutput);
        assertNotNull(changeFigureOrderOutput.getId());
        assertEquals(ExitCode.SUCCESS, changeFigureOrderOutput.getExitCode());

        board = boardRepository.findById(changeFigureOrderOutput.getId()).get();
        assertEquals(0, board.getCommittedFigures().get(0).getOrder());
        assertEquals(1, board.getCommittedFigures().get(1).getOrder());
        assertEquals(2, board.getCommittedFigures().get(2).getOrder());
    }
}
