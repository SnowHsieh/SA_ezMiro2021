package ntut.csie.sslab.miro.usecase.eventhandler;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardSession;
import ntut.csie.sslab.miro.entity.model.board.event.BoardEntered;
import ntut.csie.sslab.miro.entity.model.board.event.BoardLeft;
import ntut.csie.sslab.miro.entity.model.cursor.Cursor;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;

import java.util.List;
import java.util.UUID;

public class NotifyCursor {
    private CursorRepository cursorRepository;
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public NotifyCursor(CursorRepository cursorRepository, BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.cursorRepository = cursorRepository;
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void handleBoardEntered(BoardEntered boardEntered) {
        if(cursorRepository.findCursorByUserId(boardEntered.getUserId()).isPresent())
            return;
        Cursor cursor = new Cursor(boardEntered.getUserId(), boardEntered.getBoardId(), UUID.randomUUID().toString());
        cursorRepository.save(cursor);
        domainEventBus.postAll(cursor);
    }

    public void handleBoardLeft(BoardLeft boardLeft) {
        Board board = boardRepository.findById(boardLeft.getBoardId()).get();
        List<BoardSession> boardSessionList = board.getBoardSessions();
        if(boardSessionList.stream().anyMatch(x -> x.getUserId().equals(boardLeft.getUserId())))
            return;
        Cursor cursor = cursorRepository.findCursorByUserId(boardLeft.getUserId()).get();
        cursor.deleteCursor();
        cursorRepository.deleteById(cursor.getCursorId());
        domainEventBus.postAll(cursor);
    }
}
