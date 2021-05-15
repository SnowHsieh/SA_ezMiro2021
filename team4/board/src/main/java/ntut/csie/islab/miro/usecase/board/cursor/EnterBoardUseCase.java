package ntut.csie.islab.miro.usecase.board.cursor;

import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.BoardSession;
import ntut.csie.islab.miro.entity.model.board.BoardSessionId;
import ntut.csie.islab.miro.entity.model.board.event.BoardContentMightExpire;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import java.util.UUID;

public class EnterBoardUseCase {
    private DomainEventBus domainEventBus;
    private BoardRepository boardRepository;

    public EnterBoardUseCase(DomainEventBus domainEventBus, BoardRepository boardRepository) {
        this.domainEventBus = domainEventBus;
        this.boardRepository = boardRepository;
    }

    public EnterBoardInput newInput() {
        return new EnterBoardInput();
    }

    public void execute(EnterBoardInput input, CqrsCommandOutput output) {
        Board board = boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board) {
            output.setId(input.getBoardId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Enter board failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new BoardContentMightExpire(input.getBoardId()));
            return;
        }

        BoardSessionId boardSessionId = BoardSessionId.create();

        board.acceptUserEntry(boardSessionId, input.getUserId());

        output.setId(boardSessionId.id());
        output.setExitCode(ExitCode.SUCCESS);
    }
}
