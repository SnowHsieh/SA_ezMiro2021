package ntut.csie.islab.miro.usecase.figure.line.unattachtextfigure;


import java.util.UUID;

public class UnattachTextfigureInput {
    private UUID boardId;
    private UUID figureId;
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

    public String getAttachEndPointKind() {
        return attachEndPointKind;
    }

    public void setAttachEndPointKind(String attachEndPointKind) {
        this.attachEndPointKind = attachEndPointKind;
    }
}
