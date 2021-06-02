package ntut.csie.selab.domain;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.board.create.CreateBoardUseCase;

public class MockFactory {

    public static Board createBoard(String id) {
        String teamId = "";
        String boardName = "";
        return new Board(id, teamId, boardName);
    }

    public static Widget createStickyNoteIn(Board board, String id) {
        Widget stickyNote = new StickyNote(id, board.getId(), new Coordinate(0, 0, 10, 10));
        board.commitWidgetCreation(stickyNote.getId());
        return stickyNote;
    }
    public static Widget createStickyNoteIn(Board board, String id, Coordinate coordinate) {
        Widget stickyNote = new StickyNote(id, board.getId(), coordinate);
        board.commitWidgetCreation(stickyNote.getId());
        return stickyNote;
    }

}
