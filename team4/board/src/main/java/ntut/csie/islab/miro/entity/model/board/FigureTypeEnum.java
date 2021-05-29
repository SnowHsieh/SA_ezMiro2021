package ntut.csie.islab.miro.entity.model.board;

import ntut.csie.islab.miro.entity.model.figure.line.ArrowKindEnum;

public enum FigureTypeEnum {
    LINE, STICKYNOTE, TEXT, SHAPE;

    public static FigureTypeEnum fromInteger(int x) {
        switch (x) {
            case 0:
                return LINE;
            case 1:
                return STICKYNOTE;
            case 2:
                return TEXT;
            case 3:
                return SHAPE;
        }
        return null;
    }
}
