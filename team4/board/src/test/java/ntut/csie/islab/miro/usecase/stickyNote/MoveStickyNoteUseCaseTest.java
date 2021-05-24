package ntut.csie.islab.miro.usecase.stickyNote;

import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.textFigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
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

public class MoveStickyNoteUseCaseTest extends AbstractSpringBootJpaTest {

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

        TextFigure executedStickyNote = stickyNoteRepository.findById(UUID.fromString(output.getId())).get();
        assertNotNull(executedStickyNote);
        assertEquals(boardId,executedStickyNote.getBoardId());
        assertEquals(UUID.fromString(preGeneratedStickyNote.getId()),executedStickyNote.getFigureId());
        assertEquals(100,executedStickyNote.getPosition().getX());
        assertEquals(100,executedStickyNote.getPosition().getY());

        //todo : Need to check if Domainevent generate success!!




    }
}
