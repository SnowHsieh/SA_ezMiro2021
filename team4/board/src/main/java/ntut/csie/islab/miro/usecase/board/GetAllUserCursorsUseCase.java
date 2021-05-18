package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.presenter.getAllCursors.GetAllUserCursorsPresenter;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.cursor.Cursor;
import ntut.csie.islab.miro.entity.model.board.event.BoardContentMightExpire;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import java.util.List;

public class GetAllUserCursorsUseCase {
    private DomainEventBus domainEventBus;
    private BoardRepository boardRepository;

    public GetAllUserCursorsUseCase(DomainEventBus domainEventBus, BoardRepository boardRepository) {
        this.domainEventBus = domainEventBus;
        this.boardRepository = boardRepository;
    }

    public GetAllUserCursorsInput newInput() {
        return new GetAllUserCursorsInput();
    }
    public void execute(GetAllUserCursorsInput input, GetAllUserCursorsPresenter presenter) {
        Board board = this.boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board) {
            presenter.setBoardId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Get board failed: board not found, board id = " + input.getBoardId());

            domainEventBus.post(new BoardContentMightExpire(input.getBoardId()));
            return;
        }

        List<Cursor> cursors = board.getCursorList();
        presenter.setBoardId(board.getBoardId()).setCursorDtos(cursors);
    }
}
