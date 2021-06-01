package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.front.BringNoteToFrontInput;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.front.BringNoteToFrontUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.front.BringNoteToFrontUseCaseImpl;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BringNoteToFrontUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void bring_note1_to_front() {
        String boardId = create_board();
        String note1Id = create_note(boardId); // zindex 0
        String note2Id = create_note(boardId); // zindex 1
        eventListener.clear();
        BringNoteToFrontUseCase bringNoteToFrontUseCase = new BringNoteToFrontUseCaseImpl(figureRepository, domainEventBus);
        BringNoteToFrontInput input = bringNoteToFrontUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(note1Id); // zindex 0 -> 1

        bringNoteToFrontUseCase.execute(input, output);

        assertNotNull(output.getId());
        Map<String, CommittedFigure> committedFigures = boardRepository.findById(boardId).get().getCommittedFigures();
        assertEquals(2, committedFigures.size());
        assertEquals(1, committedFigures.get(note1Id).getZOrder());
        assertEquals(0, committedFigures.get(note2Id).getZOrder());
        assertEquals(2, eventListener.getEventCount());
    }
}