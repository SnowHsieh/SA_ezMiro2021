package ntut.csie.selab.usecase.board;

public class CommittedWidgetDto {
    private String widgetId;
    private int zOrder;

    public CommittedWidgetDto(String widgetId, int zOrder) {
        this.widgetId = widgetId;
        this.zOrder = zOrder;
    }

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
}
