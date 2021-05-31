package ntut.csie.islab.miro.usecase.eventhandler;

import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineDeletedDomainEvent;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.event.FigureCommittedDomainEvent;
import ntut.csie.islab.miro.entity.model.board.event.FigureUncommittedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote.event.StickyNoteCreatedDomainEvent;
import ntut.csie.islab.miro.entity.model.figure.textfigure.stickynote.event.StickyNoteDeleteDomainEvent;
import ntut.csie.sslab.ddd.model.DomainEventBus;

import java.util.Optional;
public class NotifyBoard {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public NotifyBoard(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }


    public void whenFigureCreated(StickyNoteCreatedDomainEvent stickyNoteCreatedDomainEvent) {
        Optional<Board> board = boardRepository.findById(stickyNoteCreatedDomainEvent.getBoardId());

        if (board.isPresent()) {
            board.get().commitFigure(stickyNoteCreatedDomainEvent.getFigureId(), FigureTypeEnum.STICKYNOTE);
            boardRepository.save(board.get());
            domainEventBus.post(new FigureCommittedDomainEvent(stickyNoteCreatedDomainEvent.getBoardId(), stickyNoteCreatedDomainEvent.getFigureId()));
        } else {
            throw new RuntimeException("Board not found, board id = " + stickyNoteCreatedDomainEvent.getBoardId());
        }
    }

    public void whenFigureCreated(LineCreatedDomainEvent lineCreatedDomainEvent) {
        Optional<Board> board = boardRepository.findById(lineCreatedDomainEvent.getBoardId());

        if (board.isPresent()) {
            board.get().commitFigure(lineCreatedDomainEvent.getFigureId(), FigureTypeEnum.LINE);
            boardRepository.save(board.get());
            domainEventBus.post(new FigureCommittedDomainEvent(lineCreatedDomainEvent.getBoardId(), lineCreatedDomainEvent.getFigureId()));
        } else {
            throw new RuntimeException("Board not found, board id = " + lineCreatedDomainEvent.getBoardId());
        }
    }

    public void whenFigureDeleted(StickyNoteDeleteDomainEvent stickyNoteDeleteDomainEvent) {
        Optional<Board> board = boardRepository.findById(stickyNoteDeleteDomainEvent.getBoardId());

        if (board.isPresent()) {
            //for board in db , not for board in memory
            board.get().uncommitFigure(stickyNoteDeleteDomainEvent.getFigureId());
            boardRepository.save(board.get());
            domainEventBus.post(new FigureUncommittedDomainEvent(stickyNoteDeleteDomainEvent.getBoardId(), stickyNoteDeleteDomainEvent.getFigureId()));
        } else {
            throw new RuntimeException("Board not found, board id = " + stickyNoteDeleteDomainEvent.getBoardId());
        }
    }

    public void whenFigureDeleted(LineDeletedDomainEvent lineDeleteDomainEvent) {
        Optional<Board> board = boardRepository.findById(lineDeleteDomainEvent.getBoardId());

        if (board.isPresent()) {
            //for board in db , not for board in memory
            board.get().uncommitFigure(lineDeleteDomainEvent.getFigureId());
            boardRepository.save(board.get());
            domainEventBus.post(new FigureUncommittedDomainEvent(lineDeleteDomainEvent.getBoardId(), lineDeleteDomainEvent.getFigureId()));
        } else {
            throw new RuntimeException("Board not found, board id = " + lineDeleteDomainEvent.getBoardId());
        }
    }

}
