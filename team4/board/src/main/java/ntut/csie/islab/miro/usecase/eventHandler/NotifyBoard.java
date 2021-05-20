package ntut.csie.islab.miro.usecase.eventHandler;

import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.event.FigureCommittedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.event.FigureUncommittedDomainEvent;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.textFigure.stickynote.event.StickyNoteDeleteDomainEvent;
import ntut.csie.sslab.ddd.model.DomainEventBus;

import java.util.Optional;
public class NotifyBoard {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public NotifyBoard(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }


    public void whenTextFigureCreated(StickyNoteCreatedDomainEvent stickyNoteCreatedDomainEvent) {
        Optional<Board> board = boardRepository.findById(stickyNoteCreatedDomainEvent.getBoardId());

        if (board.isPresent()) {
            board.get().commitFigure(stickyNoteCreatedDomainEvent.getFigureId());
            boardRepository.save(board.get());
            domainEventBus.post(new FigureCommittedDomainEvent(stickyNoteCreatedDomainEvent.getBoardId(), stickyNoteCreatedDomainEvent.getFigureId()));
        } else {
            throw new RuntimeException("Board not found, board id = " + stickyNoteCreatedDomainEvent.getBoardId());
        }
    }

    public void whenTextFigureDeleted(StickyNoteDeleteDomainEvent stickyNoteDeleteDomainEvent) {
        Optional<Board> board = boardRepository.findById(stickyNoteDeleteDomainEvent.getBoardId());

        if (board.isPresent()) {
            board.get().uncommitFigure(stickyNoteDeleteDomainEvent.getFigureId());
            boardRepository.save(board.get());
//            domainEventBus.postAll(board.get());
            domainEventBus.post(new FigureUncommittedDomainEvent(stickyNoteDeleteDomainEvent.getBoardId(), stickyNoteDeleteDomainEvent.getFigureId()));
        } else {
            throw new RuntimeException("Board not found, board id = " + stickyNoteDeleteDomainEvent.getBoardId());
        }
    }

}
