package ntut.csie.team5.usecase.board.change_order;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.entity.model.board.OrderType;
import ntut.csie.team5.usecase.ClientBoardContentMightExpire;
import ntut.csie.team5.usecase.board.BoardRepository;

public class ChangeFigureOrderUseCaseImpl implements ChangeFigureOrderUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public ChangeFigureOrderUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public ChangeFigureOrderInput newInput() {
        return new ChangeFigureOrderInputImpl();
    }

    @Override
    public void execute(ChangeFigureOrderInput input, CqrsCommandOutput output) {
        Board board  = boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board) {
            output.setId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Change figure order failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        switch (input.getOrderType()) {
            case FRONT:
                board.moveFigureOrderFront(input.getFigureId());
                break;
            case BACK:
                board.moveFigureOrderBack(input.getFigureId());
                break;
            case FRONT_END:
                board.moveFigureOrderFrontEnd(input.getFigureId());
                break;
            case BACK_END:
                board.moveFigureOrderBackEnd(input.getFigureId());
                break;
        }

        boardRepository.save(board);
        domainEventBus.postAll(board);

        output.setId(board.getBoardId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class ChangeFigureOrderInputImpl implements ChangeFigureOrderInput {

        private String boardId;
        private String figureId;
        private OrderType orderType;

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
        public OrderType getOrderType() {
            return orderType;
        }

        @Override
        public void setOrderType(OrderType orderType) {
            this.orderType = orderType;
        }
    }
}
