package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.OrderType;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.board.order.ChangeFigureOrderInput;
import ntut.csie.team5.usecase.board.order.ChangeFigureOrderUseCase;
import ntut.csie.team5.usecase.board.order.ChangeFigureOrderUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ChangeFigureOrderUseCaseTest extends AbstractTest {

    public ChangeFigureOrderUseCaseTest() {
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

        changeFigureOrderUseCase.execute(changeFigureOrderInput, changeFigureOrderOutput);
        assertNotNull(changeFigureOrderOutput.getId());
        assertEquals(ExitCode.SUCCESS, changeFigureOrderOutput.getExitCode());
    }
}
