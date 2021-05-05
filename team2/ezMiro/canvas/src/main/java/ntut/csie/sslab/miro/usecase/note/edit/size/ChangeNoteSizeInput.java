package ntut.csie.sslab.miro.usecase.note.edit.size;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeNoteSizeInput extends Input {
    void setNoteId(String noteId);

    String getNoteId();

    void setHeight(double height);

    double getHeight();

    void setWidth(double width);

    double getWidth();
}
