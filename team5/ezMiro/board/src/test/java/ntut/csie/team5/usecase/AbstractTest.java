package ntut.csie.team5.usecase;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.team5.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.team5.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.team5.adapter.gateway.repository.springboot.figure.line.LineRepositoryImpl;
import ntut.csie.team5.adapter.gateway.repository.springboot.figure.line.LineRepositoryPeer;
import ntut.csie.team5.adapter.gateway.repository.springboot.figure.note.NoteRepositoryImpl;
import ntut.csie.team5.adapter.gateway.repository.springboot.figure.note.NoteRepositoryPeer;
import ntut.csie.team5.adapter.project.ProjectRepositoryImpl;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.usecase.board.BoardRepository;
import ntut.csie.team5.usecase.board.create.CreateBoardInput;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCase;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.team5.usecase.board.enter.EnterBoardInput;
import ntut.csie.team5.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.team5.usecase.board.enter.EnterBoardUseCaseImpl;
import ntut.csie.team5.usecase.eventhandler.NotifyBoard;
import ntut.csie.team5.usecase.figure.connectable_figure.note.NoteRepository;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCaseImpl;
import ntut.csie.team5.usecase.figure.line.LineRepository;
import ntut.csie.team5.usecase.project.ProjectRepository;
import ntut.csie.team5.usecase.project.create.CreateProjectInput;
import ntut.csie.team5.usecase.project.create.CreateProjectUseCase;
import ntut.csie.team5.usecase.project.create.CreateProjectUseCaseImpl;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes= JpaApplicationTest.class)
@TestPropertySource(locations = "classpath:test.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractTest {

    public ProjectRepository projectRepository;
    public BoardRepository boardRepository;
    public NoteRepository noteRepository;
    public LineRepository lineRepository;
    public DomainEventBus domainEventBus;

    public NotifyBoard notifyBoard;

    public String teamId;
    public String projectId;
    public String projectName;
    public String userId;
    public String boardId;
    public String boardName;
    public int defaultLeftTopPositionX;
    public int defaultLeftTopPositionY;
    public int defaultHeight;
    public int defaultWidth;
    public String defaultColor;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Autowired
    private NoteRepositoryPeer noteRepositoryPeer;

    @Autowired
    private LineRepositoryPeer lineRepositoryPeer;

    @Before
    public void setUp() throws Exception {
        projectRepository = new ProjectRepositoryImpl();
        boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        noteRepository = new NoteRepositoryImpl(noteRepositoryPeer);
        lineRepository = new LineRepositoryImpl(lineRepositoryPeer);
        domainEventBus = new DomainEventBus();

        notifyBoard = new NotifyBoard(boardRepository, domainEventBus);

        teamId = UUID.randomUUID().toString();
        projectId = UUID.randomUUID().toString();
        projectName = "ezmiro project";
        userId = UUID.randomUUID().toString();
        boardId = UUID.randomUUID().toString();
        boardName = "ezmiro board";
        defaultLeftTopPositionX = 0;
        defaultLeftTopPositionY = 0;
        defaultHeight = 10;
        defaultWidth = 10;
        defaultColor = "#000000";
    }

    public String createProject(String teamId, String projectName) {
        CreateProjectUseCase createProjectUseCase = new CreateProjectUseCaseImpl(projectRepository, domainEventBus);
        CreateProjectInput createProjectInput = createProjectUseCase.newInput();
        CqrsCommandPresenter createProjectOutput = CqrsCommandPresenter.newInstance();

        createProjectInput.setTeamId(teamId);
        createProjectInput.setProjectName(projectName);

        createProjectUseCase.execute(createProjectInput, createProjectOutput);

        return createProjectOutput.getId();
    }

    public String createBoard(String projectId, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardOutput = CqrsCommandPresenter.newInstance();

        createBoardInput.setProjectId(projectId);
        createBoardInput.setBoardName(boardName);

        createBoardUseCase.execute(createBoardInput, createBoardOutput);
        return createBoardOutput.getId();
    }

    public String postNote(String boardId, int leftTopPositionX, int leftTopPositionY, int height, int width, String color) {
        PostNoteUseCase postNoteUseCase = new PostNoteUseCaseImpl(noteRepository, domainEventBus);
        PostNoteInput postNoteInput = postNoteUseCase.newInput();
        CqrsCommandPresenter postNoteOutput = CqrsCommandPresenter.newInstance();

        postNoteInput.setBoardId(boardId);
        postNoteInput.setLeftTopPositionX(leftTopPositionX);
        postNoteInput.setLeftTopPositionY(leftTopPositionY);
        postNoteInput.setHeight(height);
        postNoteInput.setWidth(width);
        postNoteInput.setColor(color);
        postNoteInput.setFigureType(FigureType.NOTE);

        postNoteUseCase.execute(postNoteInput, postNoteOutput);
        return postNoteOutput.getId();
    }

    public String enterBoard(String boardId, String userId) {
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCaseImpl(boardRepository, domainEventBus);
        EnterBoardInput enterBoardInput = enterBoardUseCase.newInput();
        CqrsCommandPresenter enterBoardOutput = CqrsCommandPresenter.newInstance();

        enterBoardInput.setBoardId(boardId);
        enterBoardInput.setUserId(userId);

        enterBoardUseCase.execute(enterBoardInput, enterBoardOutput);

        return enterBoardOutput.getId();
    }
}
