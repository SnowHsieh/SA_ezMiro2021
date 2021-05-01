package ntut.csie.islab.miro.usecase.stickyNote;

import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.textFigure.Position;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteMovedDomainEvent;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.CreateStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.MoveStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.MoveStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MoveStickyNoteUseCaseTest {
    public DomainEventBus domainEventBus;
    public TextFigureRepository stickyNoteRepository;
    private UUID boardId;
    private CqrsCommandPresenter preGeneratedStickyNote;
    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        stickyNoteRepository = new TextFigureRepository();
        boardId = UUID.randomUUID();
        preGeneratedStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(0,0),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#f9f900"));

    }

    private CqrsCommandPresenter generateCreateStickyNoteUseCaseOutput(UUID id, Position position, String content, Style style){
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();
        input.setBoardId(id);
        input.setPosition(position.getX(),position.getY());
        input.setContent(content);
        input.setStyle(style);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        createStickyNoteUseCase.execute(input, output);

        return output;
    }

    @Test
    public void test_move_sticky_note(){

        MoveStickyNoteUseCase moveStickyNoteUseCase = new MoveStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        MoveStickyNoteInput input = moveStickyNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(UUID.fromString(preGeneratedStickyNote.getId()));
        input.setNewPosition(new Position(100,100));
        moveStickyNoteUseCase.execute(input, output);


        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());

        TextFigure executedStickyNote = stickyNoteRepository.findById(boardId, UUID.fromString(output.getId())).get();
        assertNotNull(executedStickyNote);
        assertEquals(boardId,executedStickyNote.getBoardId());
        assertEquals(UUID.fromString(preGeneratedStickyNote.getId()),executedStickyNote.getFigureId());
        assertEquals(100,executedStickyNote.getPosition().getX());
        assertEquals(100,executedStickyNote.getPosition().getY());

        //todo : Need to check if Domainevent generate success!!




    }
}
