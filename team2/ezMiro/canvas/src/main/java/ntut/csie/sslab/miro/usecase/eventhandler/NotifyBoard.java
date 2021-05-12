package ntut.csie.sslab.miro.usecase.eventhandler;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.note.event.NoteBroughtToFront;
import ntut.csie.sslab.miro.entity.model.note.event.NoteCreated;
import ntut.csie.sslab.miro.entity.model.note.event.NoteDeleted;
import ntut.csie.sslab.miro.entity.model.note.event.NoteSentToBack;
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

    public void whenNoteCreated(NoteCreated noteCreated) {
        Board board = boardRepository.findById(noteCreated.boardId()).get();
        int zOrder = board.getCommittedFigures().size();
        board.commitFigure(board.getId(), noteCreated.noteId(), zOrder);
    }

    public void whenNoteBroughtToFront(NoteBroughtToFront noteBroughtToFront) {
        Board board = boardRepository.findById(noteBroughtToFront.boardId()).get();
        board.commitNoteToFront(board.getId(), noteBroughtToFront.noteId());
    }

    public void whenNoteSentToBack(NoteSentToBack noteSentToBack) {
        Board board = boardRepository.findById(noteSentToBack.boardId()).get();
        board.commitNoteToBack(board.getId(), noteSentToBack.noteId());
    }

    public void whenNoteDeleted(NoteDeleted noteDeleted) {
        Board board = boardRepository.findById(noteDeleted.boardId()).get();
        board.removeNoteFromBoard(board.getId(), noteDeleted.noteId());
    }
}