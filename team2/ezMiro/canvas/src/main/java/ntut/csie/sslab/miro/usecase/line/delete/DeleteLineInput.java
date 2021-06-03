package ntut.csie.sslab.miro.usecase.line.delete;

import ntut.csie.sslab.ddd.usecase.Input;

public interface DeleteLineInput extends Input {
    void setLineId(String lineId);

    String getLineId();

    void setBoardId(String boardId);

    String getBoardId();
}