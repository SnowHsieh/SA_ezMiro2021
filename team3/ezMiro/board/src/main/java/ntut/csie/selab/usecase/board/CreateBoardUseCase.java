package ntut.csie.selab.usecase.board;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.create.BoardCreated;
import ntut.csie.selab.usecase.board.create.CreateBoardInput;
import ntut.csie.selab.usecase.board.create.CreateBoardOutput;
import ntut.csie.selab.entity.model.Board;

import java.util.Date;
import java.util.UUID;

public class CreateBoardUseCase {

    private BoardRepositoryImpl boardRepositoryImpl;
    private DomainEventBus domainEventBus;
    public CreateBoardUseCase(BoardRepositoryImpl boardRepositoryImpl, DomainEventBus domainEventBus) {
        this.boardRepositoryImpl = boardRepositoryImpl;
        this.domainEventBus = domainEventBus;
    }

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        String boardId = UUID.randomUUID().toString();
        Board board = new Board(boardId, input.getTeamId(), input.getBoardName());

        boardRepositoryImpl.add(board);
        domainEventBus.post(new BoardCreated(new Date(), boardId, input.getTeamId()));

        output.setBoardId(board.getId());
        output.setTeamId(board.getTeamId());
        output.setBoardName(board.getBoardName());
    }
}
