package ntut.csie.team5.usecase.figure.line.delete;

import ntut.csie.sslab.ddd.usecase.Input;

public interface DeleteLineInput extends Input {

    String getFigureId();

    void setFigureId(String figureId);
}
