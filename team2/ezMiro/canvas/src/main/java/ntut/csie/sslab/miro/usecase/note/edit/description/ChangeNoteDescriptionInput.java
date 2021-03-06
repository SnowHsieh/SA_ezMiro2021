package ntut.csie.sslab.miro.usecase.note.edit.description;

import ntut.csie.sslab.ddd.usecase.Input;

public interface ChangeNoteDescriptionInput extends Input {
    void setNoteId(String noteId);

    String getNoteId();

    void setDescription(String description);

    String getDescription();
}
