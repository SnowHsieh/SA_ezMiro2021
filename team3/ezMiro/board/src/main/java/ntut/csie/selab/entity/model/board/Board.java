package ntut.csie.selab.entity.model.board;

import ntut.csie.selab.entity.model.board.event.BoardCreated;
import ntut.csie.selab.model.AggregateRoot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Board extends AggregateRoot<String> {
    private String teamId;
    private String boardName;
    private List<String> widgetIds;

    public Board(String id, String teamId, String boardName) {
        super(id);
        this.teamId = teamId;
        this.boardName = boardName;
        this.widgetIds = new ArrayList<>();

        addDomainEvent(new BoardCreated(new Date(), id, teamId));
    }

    public String getBoardId() {
        return getId();
    }

    public String getTeamId() {
        return teamId;
    }

    public String getBoardName() {
        return boardName;
    }

    public List<String> getWidgetIds() {
        return widgetIds;
    }

    public void commitWidgetCreation(String boardId, String widgetId) {
        if (id.equals(boardId)) {
            widgetIds.add(widgetId);
        }
    }

    public void commitWidgetDeletion(String boardId, String widgetId) {
        if (id.equals(boardId)) {
            widgetIds.remove(widgetId);
        }
    }
}
