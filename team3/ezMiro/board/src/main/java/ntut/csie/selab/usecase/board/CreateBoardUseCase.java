package ntut.csie.selab.usecase.board;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.create.BoardCreated;
import ntut.csie.selab.usecase.board.create.CreateBoardInput;
import ntut.csie.selab.usecase.board.create.CreateBoardOutput;

import java.util.Date;
import java.util.UUID;

public class CreateBoardUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public CreateBoardUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        String boardId = UUID.randomUUID().toString();
        Board board = new Board(boardId, input.getTeamId(), input.getBoardName());

        boardRepository.add(board);
        domainEventBus.post(new BoardCreated(new Date(), boardId, input.getTeamId()));

        output.setBoardId(board.getId());
        output.setTeamId(board.getTeamId());
        output.setBoardName(board.getBoardName());
    }
}
