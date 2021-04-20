package ntut.csie.sslab.miro.usecase.note.edit;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeNoteColorInput extends Input {
    void setNoteId(String noteId);

    String getNoteId();

    void setBoardId(String boardId);

    String getBoardId();

    void setNewColor(String color);

    String getNewColor();
}
