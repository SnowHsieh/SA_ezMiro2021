package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.BoardSessionId;
import ntut.csie.islab.miro.entity.model.board.event.BoardContentMightExpire;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class LeaveBoardUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public LeaveBoardUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public LeaveBoardInput newInput() {
        return new LeaveBoardInput();
    }

    public void execute(LeaveBoardInput input, CqrsCommandPresenter output) {
        Board board = boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board) {
            output.setId(input.getBoardId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Leave board failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new BoardContentMightExpire(input.getBoardId()));
            return;
        }

        BoardSessionId boardSessionId = BoardSessionId.valueOf(input.getBoardSessionId());

        board.acceptUserLeaving(boardSessionId, input.getUserId());

        output.setId(boardSessionId.id());
        output.setExitCode(ExitCode.SUCCESS);

    }
}
