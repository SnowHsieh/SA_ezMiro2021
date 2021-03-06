package ntut.csie.islab.miro.usecase.stickyNote;

import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.ChangeStickyNoteColorInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.ChangeStickyNoteColorUseCase;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeStickyNoteColorUseCaseTest {
    public DomainEventBus domainEventBus;
    public TextFigureRepository stickyNoteRepository;
    private UUID boardId;
    private CqrsCommandPresenter preGeneratedStickyNote;

    @BeforeEach
    public void setUp() {
        domainEventBus = new GoogleEventBus();
        stickyNoteRepository = new TextFigureRepository();
        boardId = UUID.randomUUID();
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
    public void test_change_sticky_note_color() {
        ChangeStickyNoteColorUseCase changeStickyNoteColorUseCase = new ChangeStickyNoteColorUseCase(stickyNoteRepository,  domainEventBus);
        ChangeStickyNoteColorInput input = changeStickyNoteColorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(UUID.fromString(preGeneratedStickyNote.getId()));
        input.setColor("#f9f900");
        changeStickyNoteColorUseCase.execute(input,output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
        assertEquals("#f9f900",stickyNoteRepository.findById(boardId, UUID.fromString(output.getId())).get().getStyle().getColor());


    }
}
