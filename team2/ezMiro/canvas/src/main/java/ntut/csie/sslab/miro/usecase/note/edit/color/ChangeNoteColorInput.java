package ntut.csie.sslab.miro.usecase.note.edit.color;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeNoteColorInput extends Input {
    void setNoteId(String noteId);

    String getNoteId();

    void setColor(String color);

    String getColor();
}
