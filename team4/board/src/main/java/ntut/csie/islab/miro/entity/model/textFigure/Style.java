package ntut.csie.islab.miro.entity.model.textFigure;

public class Style {
    private int fontSize;
    private ShapeKindEnum shape;
    private double width;
    private double height;
    private String color;

    public Style(int fontSize, ShapeKindEnum shape, double width, double height, String color) {
        this.fontSize = fontSize;
        this.shape = shape;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public ShapeKindEnum getShape() {
        return shape;
    }

    public void setShape(ShapeKindEnum shape) {
        this.shape = shape;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
