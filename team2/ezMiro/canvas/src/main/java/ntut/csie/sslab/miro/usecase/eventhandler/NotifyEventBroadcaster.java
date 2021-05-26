package ntut.csie.sslab.miro.usecase.eventhandler;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.RemoteDomainEvent;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.cursor.event.CursorEvents;
import ntut.csie.sslab.miro.entity.model.note.event.NoteEvents;
import ntut.csie.sslab.miro.usecase.EventBroadcaster;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class NotifyEventBroadcaster {

    private EventBroadcaster eventBroadcaster;
    private BoardRepository boardRepository;
    private CursorRepository cursorRepository;
    private ExecutorService executor;
    public static final String BOARD = "BOARD";

    public NotifyEventBroadcaster(EventBroadcaster eventBroadcaster,
                                         BoardRepository boardRepository,
                                         CursorRepository cursorRepository,
                                         ExecutorService executor) {

        this.eventBroadcaster = eventBroadcaster;
        this.boardRepository = boardRepository;
        this.cursorRepository = cursorRepository;
        this.executor = executor;
    }

    @Subscribe
    public void handleCursorEvent(CursorEvents.CursorCreated event) {
        broadcast(new RemoteDomainEvent(event, "Cursor", DateProvider.now()), event.getBoardId(), "cursor");
    }

    @Subscribe
    public void handleNoteEvent(NoteEvents.NoteCreated event) {
        broadcast(new RemoteDomainEvent(event, "Note", DateProvider.now()), event.getBoardId(), "note");
    }

    private void broadcast(RemoteDomainEvent remoteDomainEvent, String boardId, String subChannel){

        Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()){
            throw new RuntimeException("Board not found, board id = " + boardId);
        }
        String boardChannel = board.get().getBoardChannel();

        eventBroadcaster.broadcast(remoteDomainEvent, boardChannel, subChannel);
    }
}