package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineInput;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineUseCase;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteLineUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void delete_line() {
        String boardId = create_board();
        String lineId = create_line_without_connected_figures(boardId);
        eventListener.clear();
        DeleteLineUseCase deleteLineUseCase = new DeleteLineUseCaseImpl(figureRepository, domainEventBus);
        DeleteLineInput input = deleteLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setLineId(lineId);
        input.setBoardId(boardId);

        deleteLineUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(0, figureRepository.findByBoardId(boardId).size());
        assertFalse(figureRepository.findById(output.getId()).isPresent());
        assertEquals(0, boardRepository.findById(boardId).get().getCommittedFigures().size());
        assertEquals(2, eventListener.getEventCount());
    }
}