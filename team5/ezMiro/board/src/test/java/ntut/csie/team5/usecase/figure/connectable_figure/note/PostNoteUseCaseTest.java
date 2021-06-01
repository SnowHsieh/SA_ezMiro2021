package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCaseImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class PostNoteUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_post_note() {
        PostNoteUseCase postNoteUseCase = new PostNoteUseCaseImpl(noteRepository, domainEventBus);
        PostNoteInput postNoteInput = postNoteUseCase.newInput();
        CqrsCommandPresenter postNoteOutput = CqrsCommandPresenter.newInstance();

        postNoteInput.setBoardId(boardId);
        postNoteInput.setLeftTopPositionX(defaultLeftTopPositionX);
        postNoteInput.setLeftTopPositionY(defaultLeftTopPositionY);
        postNoteInput.setHeight(defaultHeight);
        postNoteInput.setWidth(defaultWidth);
        postNoteInput.setColor(defaultColor);
        postNoteInput.setFigureType(FigureType.NOTE);

        postNoteUseCase.execute(postNoteInput, postNoteOutput);

        assertNotNull(postNoteOutput.getId());
        assertEquals(ExitCode.SUCCESS, postNoteOutput.getExitCode());

        Note note = noteRepository.findById(postNoteOutput.getId()).orElse(null);
        assertNotNull(note);
        assertEquals(postNoteOutput.getId(), note.getId());
        assertEquals(boardId, note.getBoardId());
        assertEquals(defaultLeftTopPositionX, note.getLeftTopPositionX());
        assertEquals(defaultLeftTopPositionY, note.getLeftTopPositionY());
        assertEquals(defaultHeight, note.getHeight());
        assertEquals(defaultWidth, note.getWidth());
        assertEquals(defaultColor, note.getColor());
        assertEquals(FigureType.NOTE, note.getFigureType());
    }

    @Test
    public void should_succeed_when_post_note_in_board() {
        String boardId = createBoard(projectId, boardName);
        Board board = boardRepository.findById(boardId).orElse(null);
        assertNotNull(board);

        String firstNodeId = postNote(boardId, 1, 1, 11, 11, "#ff0000");

        board.commitFigure(firstNodeId);
        boardRepository.save(board);

        board = boardRepository.findById(boardId).orElse(null);
        assertNotNull(board);
        assertEquals(1, board.getCommittedFigures().size());


        String secondNodeId = postNote(boardId, 5, 5, 6, 6, "#0000ff");

        board.commitFigure(secondNodeId);
        boardRepository.save(board);

        board = boardRepository.findById(boardId).orElse(null);
        assertNotNull(board);
        assertEquals(2, board.getCommittedFigures().size());
    }
}
