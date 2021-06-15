package ntut.csie.selab.usecase.board.enterboard;

import ntut.csie.selab.entity.model.board.Cursor;

import java.util.Set;

public class EnterBoardOutput {
    private Cursor cursor;

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }
}
