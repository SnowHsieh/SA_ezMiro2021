package ntut.csie.selab.usecase.board.leaveboard;

import ntut.csie.selab.entity.model.board.Cursor;

import java.util.Set;

public class LeaveBoardOutput {
    private int cursorCountInBoard;

    private Set<Cursor> cursors;

    public void setCursorCountInBoard(int cursorCountInBoard) {
        this.cursorCountInBoard = cursorCountInBoard;
    }

    public int getCursorCountInBoard() {
        return cursorCountInBoard;
    }

    public void setCursors(Set<Cursor> cursors) {
        this.cursors = cursors;
    }

    public Set<Cursor> getCursors() {
        return cursors;
    }
}
