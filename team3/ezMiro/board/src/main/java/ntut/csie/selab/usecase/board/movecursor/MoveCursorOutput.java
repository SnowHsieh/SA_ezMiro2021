package ntut.csie.selab.usecase.board.movecursor;

import ntut.csie.selab.entity.model.board.Cursor;

import javax.swing.*;
import java.util.Set;

public class MoveCursorOutput {
    private String boardId;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
