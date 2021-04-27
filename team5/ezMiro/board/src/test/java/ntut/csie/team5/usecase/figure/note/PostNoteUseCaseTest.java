package ntut.csie.team5.usecase.figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.AbstractTest;
import ntut.csie.team5.usecase.figure.note.post.PostNoteInput;
import ntut.csie.team5.usecase.figure.note.post.PostNoteUseCase;
import ntut.csie.team5.usecase.figure.note.post.PostNoteUseCaseImpl;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PostNoteUseCaseTest extends AbstractTest {

    @Test
    public void should_succeed_when_post_note() {
        PostNoteUseCase postNoteUseCase = new PostNoteUseCaseImpl(figureRepository, domainEventBus);
        PostNoteInput postNoteInput = postNoteUseCase.newInput();
        CqrsCommandPresenter postNoteOutput = CqrsCommandPresenter.newInstance();

        postNoteInput.setBoardId(boardId);
        postNoteInput.setLeftTopPosition(defaultLeftTopPosition);
        postNoteInput.setRightBottomPosition(defaultRightBottomPosition);
        postNoteInput.setColor(defaultColor);
        postNoteInput.setFigureType(FigureType.NOTE);

        postNoteUseCase.execute(postNoteInput, postNoteOutput);

        assertNotNull(postNoteOutput.getId());
        assertEquals(ExitCode.SUCCESS, postNoteOutput.getExitCode());

        Figure figure = figureRepository.findById(postNoteOutput.getId()).get();
        assertTrue(figure instanceof Note);
        Note note = (Note) figure;
        assertEquals(postNoteOutput.getId(), note.getId());
        assertEquals(boardId, note.getBoardId());
        assertEquals(defaultLeftTopPosition, note.getLeftTopPosition());
        assertEquals(defaultRightBottomPosition, note.getRightBottomPosition());
        assertEquals(defaultColor, note.getColor());
        assertEquals(FigureType.NOTE, note.getFigureType());
    }

    @Test
    public void should_succeed_when_post_note_in_board() {
        String boardId = createBoard(projectId, boardName);
        Board board = boardRepository.findById(boardId).get();

        String firstNodeId = postNote(boardId, new Point(1,1), new Point(11,11), Color.RED);

        board.commitFigure(firstNodeId);
        boardRepository.save(board);

        board = boardRepository.findById(boardId).get();
        assertEquals(1, board.getCommittedFigures().size());


        String secondNodeId = postNote(boardId, new Point(5,5), new Point(11,11), Color.BLUE);

        board.commitFigure(secondNodeId);
        boardRepository.save(board);

        board = boardRepository.findById(boardId).get();
        assertEquals(2, board.getCommittedFigures().size());
    }
}
