package ntut.csie.islab.miro.entity.model.figure.connectablefigure;

public class Style {
    private int fontSize;
    private ShapeKindEnum shape;
    private String color;

    public Style(int fontSize, ShapeKindEnum shape, String color) {
        this.fontSize = fontSize;
        this.shape = shape;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
