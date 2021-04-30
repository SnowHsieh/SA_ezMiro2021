package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.connectable_figure.note.delete.DeleteNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.delete.DeleteNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.delete.DeleteNoteUseCaseImpl;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCaseImpl;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class DeleteNoteUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_delete_note() {
        PostNoteUseCase postNoteUseCase = new PostNoteUseCaseImpl(figureRepository, domainEventBus);
        PostNoteInput postNoteInput = postNoteUseCase.newInput();
        CqrsCommandPresenter postNoteOutput = CqrsCommandPresenter.newInstance();

        postNoteInput.setBoardId(boardId);
        postNoteInput.setLeftTopPosition(defaultLeftTopPosition);
        postNoteInput.setColor(defaultColor);

        postNoteUseCase.execute(postNoteInput, postNoteOutput);

        String noteId = postNoteOutput.getId();

        DeleteNoteUseCase deleteNoteUseCase = new DeleteNoteUseCaseImpl(figureRepository, domainEventBus);
        DeleteNoteInput deleteNoteInput = deleteNoteUseCase.newInput();
        CqrsCommandPresenter deleteNoteOutput = CqrsCommandPresenter.newInstance();

        deleteNoteInput.setBoardId(boardId);
        deleteNoteInput.setPosition(defaultLeftTopPosition);
        deleteNoteInput.setColor(defaultColor);
        deleteNoteInput.setNoteId(noteId);

        deleteNoteUseCase.execute(deleteNoteInput, deleteNoteOutput);

        assertNotNull(deleteNoteOutput.getId());
        assertEquals(ExitCode.SUCCESS, deleteNoteOutput.getExitCode());
        assertFalse(figureRepository.findById(deleteNoteOutput.getId()).isPresent());
    }

    @Test
    public void should_succeed_when_delete_note_in_board() {
        String boardId = createBoard(projectId, boardName);
        Board board = boardRepository.findById(boardId).get();

        String firstNodeId = postNote(boardId, new Point(1,1), 11, 11, "#ff0000");

        board.commitFigure(firstNodeId);
        boardRepository.save(board);

        board = boardRepository.findById(boardId).get();
        assertEquals(1, board.getCommittedFigures().size());

        board.uncommitFigure(firstNodeId);
        boardRepository.save(board);

        board = boardRepository.findById(boardId).get();
        assertEquals(0, board.getCommittedFigures().size());
    }
}
