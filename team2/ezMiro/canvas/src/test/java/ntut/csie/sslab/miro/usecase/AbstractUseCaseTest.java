package ntut.csie.sslab.miro.usecase;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.JpaApplicationTest;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.NotifyBoardAdapter;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.NotifyCursorAdapter;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.cursor.CursorRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.FigureRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.LineRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.NoteRepositoryPeer;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorInput;
import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorUseCase;
import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorUseCaseImpl;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoard;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyCursor;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineInput;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCase;
import ntut.csie.sslab.miro.usecase.line.create.CreateLineUseCaseImpl;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= JpaApplicationTest.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractUseCaseTest {
    protected BoardRepository boardRepository;
    protected DomainEventBus domainEventBus;
    protected DomainEventListener eventListener;
    protected CursorRepository cursorRepository;
    protected NotifyCursorAdapter notifyCursorAdapter;
    protected FigureRepository figureRepository;
    protected NotifyBoardAdapter notifyBoardAdapter;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Autowired
    private NoteRepositoryPeer noteRepositoryPeer;

    @Autowired
    private LineRepositoryPeer lineRepositoryPeer;

    @BeforeEach
    void setUp() {
        figureRepository = new FigureRepositoryImpl(noteRepositoryPeer, lineRepositoryPeer);
        domainEventBus = new DomainEventBus();
        boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        cursorRepository = new CursorRepositoryImpl();
        notifyCursorAdapter = new NotifyCursorAdapter(new NotifyCursor(cursorRepository, domainEventBus));
        notifyBoardAdapter = new NotifyBoardAdapter(new NotifyBoard(figureRepository, boardRepository, domainEventBus));
        eventListener = new DomainEventListener();

        domainEventBus.register(notifyBoardAdapter);
        domainEventBus.register(notifyCursorAdapter);
        domainEventBus.register(eventListener);
    }

    @AfterEach
    void tearDown() {
        boardRepository.deleteAll();
        figureRepository.deleteAll();
        cursorRepository.deleteAll();
    }

    protected String create_board() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput input = createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setTeamId("TeamId");
        input.setBoardName("Team2sBoard");

        createBoardUseCase.execute(input, output);

        return output.getId();
    }

    protected String create_note(String boardId) {
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setCoordinate(new Coordinate(9,26));

        createNoteUseCase.execute(input, output);

        return output.getId();
    }

    protected String create_note(String boardId, Coordinate coordinate) {
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setCoordinate(coordinate);

        createNoteUseCase.execute(input, output);

        return output.getId();
    }

    public String create_cursor(String boardId) {
        CreateCursorUseCase createCursorUseCase = new CreateCursorUseCaseImpl(cursorRepository, domainEventBus);
        CreateCursorInput input = createCursorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setUserId("userId");
        input.setCoordinate(new Coordinate(3, 4));

        createCursorUseCase.execute(input, output);
        return output.getId();
    }

    public String create_line_without_connected_figures(String boardId){
        CreateLineUseCase createLineUseCase = new CreateLineUseCaseImpl(figureRepository, domainEventBus);
        CreateLineInput input = createLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setStartConnectableFigureId("");
        input.setEndConnectableFigureId("");
        input.setStartOffset(new Coordinate(20, 30));
        input.setEndOffset(new Coordinate(40, 50));

        createLineUseCase.execute(input, output);
        return output.getId();
    }

    public String create_line_connected_to_figure_at_start_point(String boardId, String noteId){
        CreateLineUseCase createLineUseCase = new CreateLineUseCaseImpl(figureRepository, domainEventBus);
        CreateLineInput input = createLineUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setStartConnectableFigureId(noteId);
        input.setEndConnectableFigureId("");
        input.setStartOffset(new Coordinate(5, 5));
        input.setEndOffset(new Coordinate(40, 50));

        createLineUseCase.execute(input, output);
        return output.getId();
    }
}