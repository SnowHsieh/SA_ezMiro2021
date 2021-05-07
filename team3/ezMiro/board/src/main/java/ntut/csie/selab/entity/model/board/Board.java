package ntut.csie.selab.entity.model.board;

import ntut.csie.selab.entity.model.board.event.BoardCreated;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.AggregateRoot;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Board extends AggregateRoot<String> {
    private String teamId;
    private String boardName;
    private List<CommittedWidget> committedWidgets;

    public Board(String id, String teamId, String boardName) {
        super(id);
        this.teamId = teamId;
        this.boardName = boardName;
        this.committedWidgets = new ArrayList<>();

        addDomainEvent(new BoardCreated(new Date(), id, teamId));
    }

    public String getTeamId() {
        return teamId;
    }

    public String getBoardName() {
        return boardName;
    }

    public List<String> getWidgetIds() {
        return committedWidgets.stream().map(e -> e.getWidgetId()).collect(toList());
    }

    public List<CommittedWidget> getCommittedWidgets() {
        return committedWidgets;
    }

    public Optional<CommittedWidget> getCommittedWidgetBy(String widgetId) {
        return committedWidgets.stream().filter(e -> e.getWidgetId().equals(widgetId)).findFirst();
    }

    public void commitWidgetCreation(String boardId, String widgetId) {
        if (id.equals(boardId)) {
            committedWidgets.add(new CommittedWidget(boardId, widgetId, committedWidgets.size()));
        }
    }

    public void commitWidgetDeletion(String boardId, String widgetId) {
        if (id.equals(boardId)) {
            CommittedWidget removedCommittedWidget = getCommittedWidgetBy(widgetId).get();
            committedWidgets.remove(removedCommittedWidget);
            List<CommittedWidget> clone = new ArrayList<>();
            Collections.copy(clone, committedWidgets);
            committedWidgets.clear();
            for(int i = 0; i < clone.size(); i++) {
                int zOrder = i;
                if(clone.get(i).getZOrder() > removedCommittedWidget.getZOrder()) {
                    zOrder--;
                }
                committedWidgets.add(new CommittedWidget(widgetId, getId(), zOrder));
            }
        }
    }
}
