package ntut.csie.islab.miro.usecase;

import ntut.csie.islab.miro.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardRepositoryImpl;
import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardRepositoryPeer;
import ntut.csie.islab.miro.adapter.gateway.repository.textFigure.stickyNote.StickyNoteRepositoryImpl;
import ntut.csie.islab.miro.adapter.gateway.repository.textFigure.stickyNote.StickyNoteRepositoryPeer;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.usecase.textFigure.StickyNoteRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.usecase.board.createboard.CreateBoardInput;
import ntut.csie.islab.miro.usecase.board.createboard.CreateBoardUseCase;
import ntut.csie.islab.miro.usecase.eventHandler.NotifyBoard;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.*;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration(classes= JpaApplicationTest.class)
@TestPropertySource(locations = "classpath:test.properties")
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)

public abstract class AbstractSpringBootJpaTest {
    public DomainEventBus domainEventBus;
    public BoardRepository boardRepository;
    public StickyNoteRepository stickyNoteRepository;
    public NotifyBoardAdapter notifyBoardAdapter;
    public Board board;
    public UUID teamId;
    public UUID boardId;
    public UUID userId;
    public CqrsCommandPresenter createBoardUseCaseOutput;
    public CqrsCommandPresenter preGeneratedStickyNote;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;
    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @BeforeEach
    public void setUp() {
        domainEventBus = new GoogleEventBus();
        boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        notifyBoardAdapter = new NotifyBoardAdapter(new NotifyBoard(boardRepository, domainEventBus));

        teamId = UUID.randomUUID();
        userId = UUID.randomUUID();


        createBoardUseCaseOutput = setCreateBoard(teamId,"testBoard");
        assertNotNull(createBoardUseCaseOutput.getId()); //
        assertEquals(ExitCode.SUCCESS, createBoardUseCaseOutput.getExitCode());
        board = boardRepository.findById(UUID.fromString(createBoardUseCaseOutput.getId())).get();
        boardId = board.getBoardId();

        preGeneratedStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(0, 0),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));


    }

    public CqrsCommandPresenter setCreateBoard (UUID teamId, String boardName){
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(domainEventBus, boardRepository);
        CreateBoardInput createBoardInput = createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardUseCaseOutput = CqrsCommandPresenter.newInstance();
        createBoardInput.setTeamId(teamId);
        createBoardInput.setBoardName(boardName);
        createBoardUseCase.execute(createBoardInput, createBoardUseCaseOutput);
        return createBoardUseCaseOutput;
    }

    public CqrsCommandPresenter generateCreateStickyNoteUseCaseOutput(UUID boardId, Position position, String content, Style style) {
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();
        input.setBoardId(boardId);
        input.setPosition(position.getX(), position.getY());
        input.setContent(content);
        input.setStyle(style);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        createStickyNoteUseCase.execute(input, output);
        return output;
    }
    public CqrsCommandPresenter generateChangeStickyNoteContentUseCaseOutput(UUID boardId, UUID figureId, String newContent ) {
        ChangeStickyNoteContentUseCase changeStickyNoteContentUseCase = new ChangeStickyNoteContentUseCase(stickyNoteRepository, domainEventBus);
        ChangeStickyNoteContentInput input = changeStickyNoteContentUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setContent(newContent);
        changeStickyNoteContentUseCase.execute(input, output);
        return output;
    }

    public CqrsCommandPresenter generateChangeStickyNoteColorUseCaseOutput(UUID boardId, UUID figureId, String newColor ) {
        ChangeStickyNoteColorUseCase changeStickyNoteColorUseCase = new ChangeStickyNoteColorUseCase(stickyNoteRepository,  domainEventBus);
        ChangeStickyNoteColorInput input = changeStickyNoteColorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setColor(newColor);
        changeStickyNoteColorUseCase.execute(input,output);
        return output;
    }

}