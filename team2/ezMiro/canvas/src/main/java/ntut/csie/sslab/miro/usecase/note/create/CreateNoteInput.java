package ntut.csie.sslab.miro.usecase.note.create;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateNoteInput extends Input {
    String getBoardId();

    void setBoardId(String boardId);
}
