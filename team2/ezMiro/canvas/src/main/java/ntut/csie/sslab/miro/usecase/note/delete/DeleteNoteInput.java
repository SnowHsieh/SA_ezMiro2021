package ntut.csie.sslab.miro.usecase.note.delete;

import ntut.csie.sslab.ddd.usecase.Input;

public interface DeleteNoteInput extends Input {
    void setNoteId(String noteId);

    String getNoteId();

    void setBoardId(String boardId);

    String getBoardId();
}
