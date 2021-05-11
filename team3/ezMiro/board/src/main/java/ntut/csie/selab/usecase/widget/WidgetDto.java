package ntut.csie.selab.usecase.widget;

public class WidgetDto {
    private String widgetId;
    private int topLeftX;
    private int topLeftY;
    private int width;
    private int height;
    private String color;
    private String textColor;
    private String text;
    private int fontSize;

    public WidgetDto(String widgetId, int topLeftX, int topLeftY, int width, int height,
                     String color, String textColor, String text, int fontSize) {
        this.widgetId = widgetId;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.width = width;
        this.height = height;
        this.color = color;
        this.textColor = textColor;
        this.text = text;
        this.fontSize = fontSize;
    }

    public String getWidgetId() { return widgetId; }

    public int getTopLeftX() {
        return topLeftX;
    }

    public int getTopLeftY() {
        return topLeftY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getColor() {
        return color;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getText() {
        return text;
    }

    public int getFontSize() {
        return fontSize;
    }
}
