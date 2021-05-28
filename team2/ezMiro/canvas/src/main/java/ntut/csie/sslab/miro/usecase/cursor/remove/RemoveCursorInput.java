package ntut.csie.sslab.miro.usecase.cursor.remove;

import ntut.csie.sslab.ddd.usecase.Input;

public interface RemoveCursorInput extends Input {
    String getCursorId();

    void setCursorId(String cursorId);
}