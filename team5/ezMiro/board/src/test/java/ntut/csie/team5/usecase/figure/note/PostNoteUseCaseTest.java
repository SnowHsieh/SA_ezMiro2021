package ntut.csie.team5.usecase.figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.adapter.board.BoardRepositoryImpl;
import ntut.csie.team5.adapter.figure.FigureRepositoryImpl;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.board.BoardRepository;
import ntut.csie.team5.usecase.board.create.CreateBoardInput;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCase;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.team5.usecase.figure.note.post.PostNoteInput;
import ntut.csie.team5.usecase.figure.note.post.PostNoteUseCase;
import ntut.csie.team5.usecase.figure.note.post.PostNoteUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PostNoteUseCaseTest {

    private BoardRepository boardRepository;
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp() throws Exception {
        boardRepository = new BoardRepositoryImpl();
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();
    }

    @Test
    public void should_succeed_when_post_note() {
        PostNoteUseCase postNoteUseCase = new PostNoteUseCaseImpl(figureRepository, domainEventBus);
        PostNoteInput postNoteInput = postNoteUseCase.newInput();
        CqrsCommandPresenter postNoteOutput = CqrsCommandPresenter.newInstance();

        postNoteInput.setBoardId("1");
        postNoteInput.setPosition(new Point(1, 1));
        postNoteInput.setColor(Color.RED);

        postNoteUseCase.execute(postNoteInput, postNoteOutput);

        assertNotNull(postNoteOutput.getId());
        assertEquals(ExitCode.SUCCESS, postNoteOutput.getExitCode());

        Figure figure = figureRepository.findById(postNoteOutput.getId()).get();
        assertTrue(figure instanceof Note);
        Note note = (Note) figure;
        assertEquals(postNoteOutput.getId(), note.getId());
        assertEquals("1", note.getBoardId());
        assertEquals(new Point(1, 1), note.getPosition());
        assertEquals(Color.RED, note.getColor());
    }

    @Test
    public void should_succeed_when_post_note_in_board() {
        String boardId = createBoard();
        Board board = boardRepository.findById(boardId).get();

        String firstNodeId = postNote(boardId, new Point(1,1), Color.RED);

        board.commitFigure(firstNodeId);
        boardRepository.save(board);

        board = boardRepository.findById(boardId).get();
        assertEquals(1, board.getCommittedFigures().size());


        String secondNodeId = postNote(boardId, new Point(5,5), Color.BLUE);

        board.commitFigure(secondNodeId);
        boardRepository.save(board);

        board = boardRepository.findById(boardId).get();
        assertEquals(2, board.getCommittedFigures().size());
    }

    private String createBoard() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardOutput = CqrsCommandPresenter.newInstance();

        createBoardInput.setProjectId("1");
        createBoardInput.setBoardName("Board Name");

        createBoardUseCase.execute(createBoardInput, createBoardOutput);
        return createBoardOutput.getId();
    }

    private String postNote(String boardId, Point position, Color color) {
        PostNoteUseCase postNoteUseCase = new PostNoteUseCaseImpl(figureRepository, domainEventBus);
        PostNoteInput postNoteInput = postNoteUseCase.newInput();
        CqrsCommandPresenter postNoteOutput = CqrsCommandPresenter.newInstance();

        postNoteInput.setBoardId(boardId);
        postNoteInput.setPosition(position);
        postNoteInput.setColor(color);

        postNoteUseCase.execute(postNoteInput, postNoteOutput);
        return postNoteOutput.getId();
    }
}
