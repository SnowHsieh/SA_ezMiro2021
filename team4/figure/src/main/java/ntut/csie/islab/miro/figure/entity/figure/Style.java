package ntut.csie.islab.miro.entity.figure;
import ntut.csie.islab.miro.entity.figure.ShapeKindEnum;
public class Style {
    private int fontSize;
    private ShapeKindEnum shape;
    private double figureSize;
    private String color;

    public Style(int fontSize, ShapeKindEnum shape, double figureSize, String color) {
        this.fontSize = fontSize;
        this.shape = shape;
        this.figureSize = figureSize;
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

    public double getFigureSize() {
        return figureSize;
    }

    public void setFigureSize(double figureSize) {
        this.figureSize = figureSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
