package ntut.csie.sslab.kanban.usecase.cursor.delete;

import ntut.csie.sslab.ddd.usecase.Input;

public interface DeleteCursorInput extends Input {
    void setSessionId(String cursorId);

    String getSessionId();

}
