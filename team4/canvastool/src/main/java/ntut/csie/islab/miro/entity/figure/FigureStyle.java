package ntut.csie.islab.miro.entity.figure;

public class FigureStyle {
    private int fontSize;
    private FigureShapeKindEnum shape;
    private double figureSize;
    private String color;

    public FigureStyle(int fontSize, FigureShapeKindEnum shape, double figureSize, String color) {
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

    public FigureShapeKindEnum getShape() {
        return shape;
    }

    public void setShape(FigureShapeKindEnum shape) {
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
