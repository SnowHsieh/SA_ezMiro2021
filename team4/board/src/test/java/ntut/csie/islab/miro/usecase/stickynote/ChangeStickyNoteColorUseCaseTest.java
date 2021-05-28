package ntut.csie.islab.miro.usecase.stickynote;

import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.textfigure.stickynote.changecolor.ChangeStickyNoteColorInput;
import ntut.csie.islab.miro.usecase.textfigure.stickynote.changecolor.ChangeStickyNoteColorUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeStickyNoteColorUseCaseTest extends AbstractSpringBootJpaTest {


    @Test
    public void test_change_sticky_note_color() {
        ChangeStickyNoteColorUseCase changeStickyNoteColorUseCase = new ChangeStickyNoteColorUseCase(stickyNoteRepository,  domainEventBus);
        ChangeStickyNoteColorInput input = changeStickyNoteColorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(UUID.fromString(preGeneratedStickyNote.getId()));
        input.setColor("#010100");
        changeStickyNoteColorUseCase.execute(input,output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
        assertEquals("#010100",stickyNoteRepository.findById(UUID.fromString(output.getId())).get().getStyle().getColor());
    }
}
