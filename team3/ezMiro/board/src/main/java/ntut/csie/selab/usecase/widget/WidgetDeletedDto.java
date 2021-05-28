package ntut.csie.selab.usecase.widget;

public class WidgetDeletedDto {
    private String widgetId;

    public WidgetDeletedDto(String widgetId) {
        this.widgetId = widgetId;
    }

    public String getWidgetId() { return widgetId; }
}
