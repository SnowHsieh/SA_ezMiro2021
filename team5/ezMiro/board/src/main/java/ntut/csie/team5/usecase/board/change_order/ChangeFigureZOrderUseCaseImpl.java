package ntut.csie.team5.usecase.board.change_order;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.ZOrderType;
import ntut.csie.team5.usecase.ClientBoardContentMightExpire;
import ntut.csie.team5.usecase.board.BoardRepository;

public class ChangeFigureZOrderUseCaseImpl implements ChangeFigureZOrderUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public ChangeFigureZOrderUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public ChangeFigureZOrderInput newInput() {
        return new ChangeFigureZOrderInputImpl();
    }

    @Override
    public void execute(ChangeFigureZOrderInput input, CqrsCommandOutput output) {
        Board board  = boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board) {
            output.setId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Change figure order failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        board.changeFigureZOrder(input.getFigureId(), input.getZOrderType());

        boardRepository.save(board);
        domainEventBus.postAll(board);

        output.setId(board.getBoardId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class ChangeFigureZOrderInputImpl implements ChangeFigureZOrderInput {

        private String boardId;
        private String figureId;
        private ZOrderType zOrderType;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public ZOrderType getZOrderType() {
            return zOrderType;
        }

        @Override
        public void setZOrderType(ZOrderType zOrderType) {
            this.zOrderType = zOrderType;
        }
    }
}
