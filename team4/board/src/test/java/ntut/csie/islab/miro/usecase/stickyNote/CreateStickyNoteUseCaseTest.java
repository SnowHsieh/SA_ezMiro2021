package ntut.csie.islab.miro.usecase.stickyNote;


import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.textFigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
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
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(output.getId())).get().getContent());
    }

}