package ntut.csie.selab.entity.model.board;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private String id;
    private String teamId;
    private String boardName;
    private List<String> widgetIds;

    public Board(String id, String teamId, String boardName) {
        this.id = id;
        this.teamId = teamId;
        this.boardName = boardName;
        this.widgetIds = new ArrayList<>();
    }

    public String getId() {
        return id;
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
}
