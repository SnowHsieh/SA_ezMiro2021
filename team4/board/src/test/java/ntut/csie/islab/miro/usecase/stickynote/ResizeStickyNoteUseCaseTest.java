package ntut.csie.islab.miro.usecase.stickynote;

import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.resize.ResizeStickyNoteInput;
import ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.resize.ResizeStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResizeStickyNoteUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void test_resize_sticky_note() {
        ResizeStickyNoteUseCase resizeStickyNoteUseCase = new ResizeStickyNoteUseCase(stickyNoteRepository,  domainEventBus);
        ResizeStickyNoteInput input = resizeStickyNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(UUID.fromString(preGeneratedStickyNote.getId()));
        input.setWidth(100);
        input.setHeight(150);
        resizeStickyNoteUseCase.execute(input,output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
        assertEquals(100,stickyNoteRepository.findById(UUID.fromString(output.getId())).get().getStyle().getWidth());
        assertEquals(150,stickyNoteRepository.findById(UUID.fromString(output.getId())).get().getStyle().getHeight());
    }
}
