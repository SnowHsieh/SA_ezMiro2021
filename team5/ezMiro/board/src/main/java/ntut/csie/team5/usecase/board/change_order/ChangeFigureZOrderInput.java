package ntut.csie.team5.usecase.board.change_order;


import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.team5.entity.model.board.ZOrderType;

public interface ChangeFigureZOrderInput extends Input {

    String getBoardId();

    void setBoardId(String boardId);

    String getFigureId();

    void setFigureId(String figureId);

    ZOrderType getZOrderType();

    void setZOrderType(ZOrderType zOrderType);
}
