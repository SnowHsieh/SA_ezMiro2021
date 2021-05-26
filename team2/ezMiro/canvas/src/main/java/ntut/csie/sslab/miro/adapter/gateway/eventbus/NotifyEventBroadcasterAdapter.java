package ntut.csie.sslab.miro.adapter.gateway.eventbus;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.RemoteDomainEvent;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.cursor.event.CursorEvents;
import ntut.csie.sslab.miro.entity.model.note.event.NoteEvents;
import ntut.csie.sslab.miro.usecase.EventBroadcaster;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class NotifyEventBroadcasterAdapter {
    private EventBroadcaster eventBroadcaster;
    private CursorRepository cursorRepository;
    private BoardRepository boardRepository;
    private FigureRepository figureRepository;

    @Autowired
    public NotifyEventBroadcasterAdapter(EventBroadcaster eventBroadcaster, CursorRepository cursorRepository, BoardRepository boardRepository, FigureRepository figureRepository) {
        this.eventBroadcaster = eventBroadcaster;
        this.cursorRepository = cursorRepository;
        this.boardRepository = boardRepository;
        this.figureRepository = figureRepository;
    }

    @Subscribe
    public void whenCursorEventOccurred(CursorEvents event){
        Board board = boardRepository.findById(event.getBoardId()).get();
        eventBroadcaster.broadcast(new RemoteDomainEvent(event, "Cursor", event.getOccurredOn()), board.getBoardChannel(), "cursor");
    }

    @Subscribe
    public void whenNoteEventOccurred(NoteEvents event){
        Board board = boardRepository.findById(event.getBoardId()).get();
        eventBroadcaster.broadcast(new RemoteDomainEvent(event,"Note", event.getOccurredOn()), board.getBoardChannel(), "note");
    }
}