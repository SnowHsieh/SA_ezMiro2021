package ntut.csie.islab.miro.usecase.board.cursor;

import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.cursor.Cursor;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class MoveCursorUseCaseTest {
    public DomainEventBus domainEventBus;
    public BoardRepository boardRepository;
    private Board board;
    private UUID userId;

    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        boardRepository = new BoardRepository();
        board = new Board(UUID.randomUUID(),"boardName");
        boardRepository.save(board);
        userId = UUID.randomUUID();
        create_a_test_cursor();
    }

    private void create_a_test_cursor() {
        EnterBoardUseCase enterBoardUseCase = new EnterBoardUseCase(domainEventBus,boardRepository);
        EnterBoardInput enterBoardInput = enterBoardUseCase.newInput();
        CqrsCommandPresenter enterBoardOutput = CqrsCommandPresenter.newInstance();
        enterBoardInput.setBoardId(board.getBoardId());
        enterBoardInput.setUserId(userId);
        enterBoardUseCase.execute(enterBoardInput, enterBoardOutput);
    }

    @Test
    public void move_a_cursor() {

        Position newPosition = new Position(1.0,1.0);
        MoveCursorUseCase moveCursorUseCase = new MoveCursorUseCase(boardRepository, domainEventBus);
        MoveCursorInput input = moveCursorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setUserId(userId);
        input.setBoardId(board.getBoardId());
        input.setPosition(newPosition);

        moveCursorUseCase.execute(input, output);

        Board resolveBoard= boardRepository.findById(board.getBoardId()).get();
        Cursor cursor = resolveBoard.getCursorList().stream().filter(x->x.getUserId()==userId).findFirst().get();
        assertTrue(cursor.getPosition().equals(newPosition));

    }

}
