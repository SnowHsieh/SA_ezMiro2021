package ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text;

import ntut.csie.sslab.ddd.usecase.Input;

public interface EditNoteTextInput extends Input {

    String getFigureId();

    void setFigureId(String figureId);

    String getText();

    void setText(String text);
}
