package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.note.move.MoveNoteInput;
import ntut.csie.sslab.miro.usecase.note.move.MoveNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.move.MoveNoteUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MoveNoteUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void move_note() {
        String boardId = create_board();
        String noteId = create_note(boardId);
        eventListener.clear();
        MoveNoteUseCase moveNoteUseCase = new MoveNoteUseCaseImpl(figureRepository, domainEventBus);
        MoveNoteInput input = moveNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        Coordinate coordinate = new Coordinate(10,6);
        input.setNoteId(noteId);
        input.setCoordinate(coordinate);

        moveNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(coordinate, figureRepository.findById(output.getId()).get().getCoordinate());
        assertEquals(1, eventListener.getEventCount());
    }
}