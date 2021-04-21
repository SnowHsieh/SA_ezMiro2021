package ntut.csie.sslab.kanban.usecase.workspace.create;

import ntut.csie.sslab.ddd.usecase.Input;

public interface CreateWorkspaceInput extends Input {
    void setBoardId(String boardId);

    String getBoardId();
}
