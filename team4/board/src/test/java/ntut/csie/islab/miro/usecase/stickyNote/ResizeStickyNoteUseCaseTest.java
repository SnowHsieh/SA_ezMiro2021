package ntut.csie.islab.miro.usecase.stickyNote;

import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.textFigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.*;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResizeStickyNoteUseCaseTest extends AbstractSpringBootJpaTest {

    private CqrsCommandPresenter preGeneratedStickyNote;

    @BeforeEach
    public void setUp() {
        preGeneratedStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(0, 0),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));

    }
    private CqrsCommandPresenter generateCreateStickyNoteUseCaseOutput(UUID id, Position position, String content, Style style) {
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();
        input.setBoardId(id);
        input.setPosition(position.getX(), position.getY());
        input.setContent(content);
        input.setStyle(style);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        createStickyNoteUseCase.execute(input, output);

        return output;
    }
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
