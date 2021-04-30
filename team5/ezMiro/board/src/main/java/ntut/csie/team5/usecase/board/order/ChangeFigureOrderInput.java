package ntut.csie.team5.usecase.board.order;


import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.team5.entity.model.board.OrderType;

public interface ChangeFigureOrderInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    String getFigureId();

    void setFigureId(String figureId);

    OrderType getOrderType();

    void setOrderType(OrderType orderType);
}
