package ntut.csie.sslab.miro.usecase.note.edit.zorder.front;

import ntut.csie.sslab.ddd.usecase.Input;

public interface BringNoteToFrontInput extends Input {
    String getNoteId();

    void setNoteId(String noteId);
}