package ntut.csie.islab.miro.usecase.board.cursor;

import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.event.BoardContentMightExpire;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class MoveCursorUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public MoveCursorUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public MoveCursorInput newInput() {
        return new MoveCursorInput();
    }

    public void execute(MoveCursorInput input, CqrsCommandOutput output) {
        Board board = boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board) {
            output.setId(input.getBoardId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Move cursor failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new BoardContentMightExpire(input.getBoardId()));
            return;
        }
        board.setCursorPosition(input.getUserId(),input.getPosition());
        this.domainEventBus.postAll(board);
        output.setId(board.getBoardId().toString());
        output.setExitCode(ExitCode.SUCCESS);

    }
}
