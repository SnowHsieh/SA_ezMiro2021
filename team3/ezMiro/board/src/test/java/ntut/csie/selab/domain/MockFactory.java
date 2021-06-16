package ntut.csie.selab.domain;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;

public class MockFactory {

    public static Board createBoard(String id) {
        String teamId = "";
        String boardName = "";
        return new Board(id, teamId, boardName);
    }

    public static Widget createStickyNoteIn(Board board, String id) {
        Widget stickyNote = new StickyNote(id, board.getId(), new Position(0, 0, 10, 10));
        board.commitWidgetCreation(stickyNote.getId());
        return stickyNote;
    }
    public static Widget createStickyNoteIn(Board board, String id, Position position) {
        Widget stickyNote = new StickyNote(id, board.getId(), position);
        board.commitWidgetCreation(stickyNote.getId());
        return stickyNote;
    }

}
