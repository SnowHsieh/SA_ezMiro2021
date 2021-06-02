package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.widget.WidgetType;

public class LineDto {
    private String widgetId;
    private int topLeftX;
    private int topLeftY;
    private int bottomRightX;
    private int bottomRightY;
    private String type;


    public LineDto(String widgetId, int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        this.widgetId = widgetId;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
        this.type = WidgetType.LINE.getType();
    }

    public String getWidgetId() {
        return widgetId;
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public int getTopLeftY() {
        return topLeftY;
    }

    public int getBottomRightX() {
        return bottomRightX;
    }

    public int getBottomRightY() {
        return bottomRightY;
    }

    public String getType() {
        return type;
    }
}
