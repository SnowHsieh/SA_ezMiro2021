package ntut.csie.sslab.miro.usecase.eventhandler;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.event.NoteEvents;
import ntut.csie.sslab.miro.entity.model.figure.line.event.LineEvents;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class NotifyBoard {
    private FigureRepository figureRepository;
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public NotifyBoard(FigureRepository figureRepository, BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    public void whenNoteCreated(NoteEvents.NoteCreated noteCreated) {
        Board board = boardRepository.findById(noteCreated.getBoardId()).get();
        int zOrder = board.getCommittedFigures().size();
        board.commitFigure(board.getId(), noteCreated.getNoteId(), zOrder);
        domainEventBus.postAll(board);
        boardRepository.save(board);
    }

    public void whenNoteBroughtToFront(NoteEvents.NoteBroughtToFront noteBroughtToFront) {
        Board board = boardRepository.findById(noteBroughtToFront.getBoardId()).get();
        board.commitNoteToFront(board.getId(), noteBroughtToFront.getNoteId());
        domainEventBus.postAll(board);
        boardRepository.save(board);
    }

    public void whenNoteSentToBack(NoteEvents.NoteSentToBack noteSentToBack) {
        Board board = boardRepository.findById(noteSentToBack.getBoardId()).get();
        board.commitNoteToBack(board.getId(), noteSentToBack.getNoteId());
        domainEventBus.postAll(board);
        boardRepository.save(board);
    }

    public void whenNoteDeleted(NoteEvents.NoteDeleted noteDeleted) {
        Board board = boardRepository.findById(noteDeleted.getBoardId()).get();
        board.removeFigureFromBoard(board.getId(), noteDeleted.getNoteId());
        domainEventBus.postAll(board);
        boardRepository.save(board);
    }

    public void whenLineCreated(LineEvents.LineCreated lineCreated) {
        Board board = boardRepository.findById(lineCreated.getBoardId()).get();
        int zOrder = board.getCommittedFigures().size();
        board.commitFigure(board.getId(), lineCreated.getLineId(), zOrder);
        domainEventBus.postAll(board);
        boardRepository.save(board);
    }

    public void whenLineDeleted(LineEvents.LineDeleted lineDeleted) {
        Board board = boardRepository.findById(lineDeleted.getBoardId()).get();
        board.removeFigureFromBoard(board.getId(), lineDeleted.getLineId());
        domainEventBus.postAll(board);
        boardRepository.save(board);
    }
}