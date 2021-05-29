package ntut.csie.islab.miro.entity.model.figure.line;

public enum ArrowKindEnum {
    NONE,CIRCLE,ARROW;

    public static ArrowKindEnum fromInteger(int x) {
        switch(x) {
            case 0:
                return NONE;
            case 1:
                return CIRCLE;
            case 2:
                return ARROW;
        }
        return null;
    }
}
