package ntut.csie.sslab.kanban.usecase.cursor.delete;

import ntut.csie.sslab.ddd.usecase.Input;

public interface DeleteCursorInput extends Input {
    void setCursorId(String cursorId);

    String getCursorId();

}
