package ntut.csie.islab.miro.usecase.board.changefigureorder;

import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.event.BoardContentMightExpire;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class ChangeFigureOrderUseCase {
    private DomainEventBus domainEventBus;
    private BoardRepository boardRepository;

    public ChangeFigureOrderUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }
    public ChangeFigureOrderInput newInput() {
        return new ChangeFigureOrderInput();
    }


    public void execute(ChangeFigureOrderInput input, CqrsCommandPresenter output) {
        Board board = this.boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board){
            output.setId(input.getBoardId().toString())
                  .setExitCode(ExitCode.FAILURE)
                  .setMessage("Get board failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new BoardContentMightExpire(input.getBoardId()));
            return;
        }


        board.setCommittedFigureListOrder(input.getCommittedFigureListOrder());
        this.boardRepository.save(board);
        this.domainEventBus.postAll(board);
        output.setId(input.getBoardId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("ChangeFigureOrderListOnBoard success");

    }
}
