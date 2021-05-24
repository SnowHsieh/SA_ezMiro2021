package ntut.csie.islab.miro.entity.model.textFigure;

public enum ShapeKindEnum {
    TRIANGLE,
    CIRCLE,
    RECTANGLE;


    public static ShapeKindEnum fromInteger(int x) {
        switch(x) {
            case 0:
                return TRIANGLE;
            case 1:
                return CIRCLE;
            case 2:
                return RECTANGLE;
        }
        return null;
    }
}
