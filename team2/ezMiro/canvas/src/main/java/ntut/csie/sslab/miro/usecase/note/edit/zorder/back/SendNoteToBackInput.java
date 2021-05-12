package ntut.csie.sslab.miro.usecase.note.edit.zorder.back;

import ntut.csie.sslab.ddd.usecase.Input;

public interface SendNoteToBackInput extends Input {
    String getNoteId();

    void setNoteId(String noteId);
}