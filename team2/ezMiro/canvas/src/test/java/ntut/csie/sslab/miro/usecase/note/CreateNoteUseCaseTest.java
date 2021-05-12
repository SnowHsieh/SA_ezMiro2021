package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.eventbus.NotifyBoardAdapter;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note.FigureRepositoryImpl;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.usecase.DomainEventListener;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.board.CreateBoardInput;
import ntut.csie.sslab.miro.usecase.board.CreateBoardUseCase;
import ntut.csie.sslab.miro.usecase.board.CreateBoardUseCaseImpl;
import ntut.csie.sslab.miro.usecase.eventhandler.NotifyBoard;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteInput;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.create.CreateNoteUseCaseImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateNoteUseCaseTest {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;
    private DomainEventListener eventListener;
    private NotifyBoardAdapter notifyBoardAdapter;
    private BoardRepository boardRepository;

    @Before
    public void setUp() {
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();
        eventListener = new DomainEventListener();
        boardRepository = new BoardRepositoryImpl();
        notifyBoardAdapter = new NotifyBoardAdapter(new NotifyBoard(figureRepository, boardRepository, domainEventBus));

        domainEventBus.register(notifyBoardAdapter);
        domainEventBus.register(eventListener);
    }

    @Test
    public void create_note() {
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId("boardId");
        input.setCoordinate(new Coordinate(9,26));

        createNoteUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertNotNull(figureRepository.findById(output.getId()).get());
        assertEquals(9, figureRepository.findById(output.getId()).get().getCoordinate().getX());
        assertEquals(26, figureRepository.findById(output.getId()).get().getCoordinate().getY());
        assertEquals(100, figureRepository.findById(output.getId()).get().getWidth());
        assertEquals(100, figureRepository.findById(output.getId()).get().getHeight());
        assertEquals(1, eventListener.getEventCount());
    }

    @Test
    public void should_commit_figure_to_board_when_note_created(){
        String boardId = create_board();
        eventListener.clear();
        CreateNoteUseCase createNoteUseCase = new CreateNoteUseCaseImpl(figureRepository, domainEventBus);
        CreateNoteInput input = createNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardId);
        input.setCoordinate(new Coordinate(9,26));

        createNoteUseCase.execute(input, output);

        assertEquals(1, eventListener.getEventCount());
        Board board = boardRepository.findById(boardId).get();
        assertEquals(1, board.getCommittedFigures().size());
        assertEquals(output.getId(), board.getCommittedFigures().get(output.getId()).getFigureId());
        assertEquals(0, board.getCommittedFigures().get(output.getId()).getZOrder());
    }

    private String create_board() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCaseImpl(boardRepository, domainEventBus);
        CreateBoardInput input = createBoardUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setTeamId("TeamId");
        input.setBoardName("Team2sBoard");

        createBoardUseCase.execute(input, output);

        return output.getId();
    }
}