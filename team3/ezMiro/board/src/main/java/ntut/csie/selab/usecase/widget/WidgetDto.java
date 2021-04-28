package ntut.csie.selab.usecase.widget;

public class WidgetDto {
    private int topLeftX;
    private int topLeftY;
    private int width;
    private int height;


    private String color;
    private String textColor;

    public WidgetDto(int topLeftX, int topLeftY, int width, int height, String color, String textColor) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.width = width;
        this.height = height;
        this.color = color;
        this.textColor = textColor;
    }

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
}
