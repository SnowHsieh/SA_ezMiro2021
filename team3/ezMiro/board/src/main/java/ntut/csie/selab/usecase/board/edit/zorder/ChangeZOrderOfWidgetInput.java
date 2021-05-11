package ntut.csie.selab.usecase.board.edit.zorder;

public class ChangeZOrderOfWidgetInput {
    private String boardId;
    private String widgetId;
    private int zOrder;

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public int getZOrder() {
        return zOrder;
    }

    public void setZOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }
}
