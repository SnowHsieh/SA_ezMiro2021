package ntut.csie.sslab.miro.usecase.note.get;

import ntut.csie.sslab.ddd.usecase.Input;

public interface GetNoteInput extends Input {
    String getBoardId();

    void setBoardId(String boardId);

    String getNoteId();

    void setNoteId(String noteId);
}