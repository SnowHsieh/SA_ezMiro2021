package ntut.csie.sslab.miro.usecase.note.edit.displayorder;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeNoteDisplayOrderInput extends Input {
    String getNoteId();

    void setNoteId(String noteId);

    int getDisplayOrder();

    void setDisplayOrder(int displayOrder);
}
