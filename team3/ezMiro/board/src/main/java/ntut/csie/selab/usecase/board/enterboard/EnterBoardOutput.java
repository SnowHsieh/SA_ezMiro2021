package ntut.csie.selab.usecase.board.enterboard;

import ntut.csie.selab.entity.model.board.Cursor;

import java.util.Set;

public class EnterBoardOutput {
    private Set<Cursor> cursor;
    private int cursorCountInBoard;

    public Set<Cursor> getCursor() {
        return cursor;
    }

    public void setCursor(Set<Cursor> cursor) {
        this.cursor = cursor;
    }

    public int getCursorCountInBoard() {
        return cursorCountInBoard;
    }

    public void setCursorCountInBoard(int cursorCountInBoard) {
        this.cursorCountInBoard = cursorCountInBoard;
    }
}
