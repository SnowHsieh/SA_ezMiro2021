package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.back.SendNoteToBackInput;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.back.SendNoteToBackUseCase;
import ntut.csie.sslab.miro.usecase.note.edit.zorder.back.SendNoteToBackUseCaseImpl;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SendNoteToBackUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void send_note2_to_back() {
        String boardId = create_board();
        String note1Id = create_note(boardId); // zindex 0
        String note2Id = create_note(boardId); // zindex 1
        eventListener.clear();
        SendNoteToBackUseCase sendNoteToBackUseCase = new SendNoteToBackUseCaseImpl(figureRepository, domainEventBus);
        SendNoteToBackInput input = sendNoteToBackUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(note2Id); // zindex 1 -> 0

        sendNoteToBackUseCase.execute(input, output);

        assertNotNull(output.getId());
        Map<String, CommittedFigure> committedFigures = boardRepository.findById(boardId).get().getCommittedFigures();
        assertEquals(2, committedFigures.size());
        assertEquals(1, committedFigures.get(note1Id).getZOrder());
        assertEquals(0, committedFigures.get(note2Id).getZOrder());
        assertEquals(2, eventListener.getEventCount());
    }
}