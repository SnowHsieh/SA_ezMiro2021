package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteInput;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.delete.DeleteNoteUseCaseImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteNoteUseCaseTest extends AbstractUseCaseTest {

    @Test
    public void delete_note() {
        String boardId = create_board();
        String noteId = create_note(boardId);
        DeleteNoteUseCase deleteNoteUseCase = new DeleteNoteUseCaseImpl(figureRepository, domainEventBus);
        DeleteNoteInput input = deleteNoteUseCase.newInput();
        eventListener.clear();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setNoteId(noteId);
        input.setBoardId(boardId);

        deleteNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(0, figureRepository.findByBoardId(boardId).size());
        assertFalse(figureRepository.findById(output.getId()).isPresent());
        assertEquals(0, boardRepository.findById(boardId).get().getCommittedFigures().size());
        assertEquals(2, eventListener.getEventCount());
    }

    @Test
    public void should_remove_note_from_board_when_note_deleted(){
        String boardId = create_board();
        String noteId = create_note(boardId);
        eventListener.clear();
        DeleteNoteUseCase deleteNoteUseCase = new DeleteNoteUseCaseImpl(figureRepository, domainEventBus);
        DeleteNoteInput input = deleteNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setNoteId(noteId);

        deleteNoteUseCase.execute(input, output);

        assertEquals(2, eventListener.getEventCount());
        Board board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getCommittedFigures().size());
    }
}