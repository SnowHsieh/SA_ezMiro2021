package ntut.csie.sslab.kanban.usecase.cursor.create;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateCursorInput extends Input {
    void setIp(String ip);

    String getIp();

    void setBoardId(String boardId);

    String getBoardId();

    void setSessionId(String sessionId);

    String getSessionId();
}
