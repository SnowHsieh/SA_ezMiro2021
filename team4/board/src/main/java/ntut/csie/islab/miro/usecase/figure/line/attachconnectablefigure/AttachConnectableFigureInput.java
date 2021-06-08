package ntut.csie.islab.miro.usecase.figure.line.attachconnectablefigure;


import java.util.UUID;

public class AttachConnectableFigureInput {
    private UUID boardId;
    private UUID figureId;
    private UUID connectableFigureId;
    private String attachEndPointKind;
    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public UUID getFigureId() {
        return figureId;
    }

    public void setFigureId(UUID figureId) {
        this.figureId = figureId;
    }

    public UUID getConnectableFigureId() {
        return connectableFigureId;
    }

    public void setConnectableFigureId(UUID connectableFigureId) {
        this.connectableFigureId = connectableFigureId;
    }

    public String getAttachEndPointKind() {
        return attachEndPointKind;
    }

    public void setAttachEndPointKind(String attachEndPointKind) {
        this.attachEndPointKind = attachEndPointKind;
    }
}
