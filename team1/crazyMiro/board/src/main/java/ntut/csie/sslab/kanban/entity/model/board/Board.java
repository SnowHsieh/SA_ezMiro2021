package ntut.csie.sslab.kanban.entity.model.board;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.kanban.entity.model.board.event.BoardCreated;
import ntut.csie.sslab.kanban.entity.model.board.event.FigureBroughtToFront;
import ntut.csie.sslab.kanban.entity.model.board.event.FigureCommitted;
import ntut.csie.sslab.kanban.entity.model.board.event.FigureSentToBack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board extends AggregateRoot<String> {
    String boardId;
    String boardName;
    List<CommittedFigure> committedFigures;

    public Board(String boardId, String boardName) {
        super(boardId);
        this.boardId = boardId;
        this.boardName = boardName;
        this.committedFigures = new ArrayList<>();

        addDomainEvent(new BoardCreated(boardId));
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public List<CommittedFigure> getCommittedFigures() {
        return committedFigures;
    }

    public void commitFigure(String figureId) {
        committedFigures.add(new CommittedFigure(figureId, committedFigures.size()));
        addDomainEvent(new FigureCommitted(boardId, figureId));
    }

    public void bringFigureToFront(String figureId) {
        CommittedFigure figure = committedFigures.stream().filter(x-> x.getFigureId().equals(figureId)).findFirst().get();
        List<CommittedFigure> result = new ArrayList<>(committedFigures.subList(0, figure.getZOrder()));
        List<CommittedFigure> orderChangedFigures = committedFigures.subList(figure.getZOrder()+1, committedFigures.size());

        orderChangedFigures.forEach(each-> {
            result.add(new CommittedFigure(each.getFigureId(), result.size()));
        });

        result.add(new CommittedFigure(figureId, result.size()));
        committedFigures = result;
        addDomainEvent(new FigureBroughtToFront(boardId, figureId));
    }

    public void sendFigureToBack(String figureId) {
        CommittedFigure figure = committedFigures.stream().filter(x-> x.getFigureId().equals(figureId)).findFirst().get();
        List<CommittedFigure> orderChangedFigures = committedFigures.subList(0, figure.getZOrder());
        List<CommittedFigure> result = new ArrayList<>();
        List<CommittedFigure> nothingChangedFigures = committedFigures.subList(figure.getZOrder()+1, committedFigures.size());
        result.add(0, new CommittedFigure(figureId, 0));

        orderChangedFigures.forEach(each-> {
            result.add(new CommittedFigure(each.getFigureId(), result.size()));
        });

        result.addAll(nothingChangedFigures);
        committedFigures = result;
        addDomainEvent(new FigureSentToBack(boardId, figureId));
    }
}
