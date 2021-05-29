package ntut.csie.islab.miro.usecase.figure.line.create;

import ntut.csie.islab.miro.entity.model.Position;

import java.util.List;
import java.util.UUID;

public class CreateLineInput {
    private UUID boardId;
    private List<Position> positionList;
    private int strokeWidth ;
    private String color ;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
