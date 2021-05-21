package ntut.csie.islab.miro.usecase.board;

import java.util.List;
import java.util.UUID;

public class ChangeFigureOrderListOnBoardInput {
    private UUID boardId;
    private UUID FigureId;
    private List<UUID> figureIdOrderList;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public UUID getFigureId() {
        return FigureId;
    }

    public void setFigureId(UUID figureId) {
        FigureId = figureId;
    }

    public List<UUID> getCommittedFigureListOrder() {
        return figureIdOrderList;
    }

    public void setCommittedFigureListOrder(List<UUID> figureIdOrderList) {
        this.figureIdOrderList = figureIdOrderList;
    }
}
