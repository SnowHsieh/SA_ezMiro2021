package ntut.csie.islab.miro.usecase.stickynote;

import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.textfigure.TextFigure;
import ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.move.MoveStickyNoteInput;
import ntut.csie.islab.miro.usecase.figure.textfigure.stickynote.move.MoveStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
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
