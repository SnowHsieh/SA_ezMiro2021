package ntut.csie.islab.miro.usecase.stickynote;


import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.entity.model.figure.textfigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.textfigure.Style;
import ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.create.CreateStickyNoteInput;
import ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.create.CreateStickyNoteUseCase;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import java.util.UUID;

public class CreateStickyNoteUseCaseTest extends AbstractSpringBootJpaTest {


    @Test
    public void test_create_sticky_note(){
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setPosition(1.0,1.0);
        input.setContent("123");
        input.setStyle(new Style(12, ShapeKindEnum.CIRCLE, 87.87,100, "#948700"));
        createStickyNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
        assertEquals(boardId,stickyNoteRepository.findById(UUID.fromString(output.getId())).get().getBoardId());
        assertNotNull(stickyNoteRepository.findById(UUID.fromString(output.getId())).get());
        assertEquals("123", stickyNoteRepository.findById(UUID.fromString(output.getId())).get().getContent());
    }

}