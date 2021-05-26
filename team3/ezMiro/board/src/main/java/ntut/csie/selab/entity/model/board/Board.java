package ntut.csie.selab.entity.model.board;

import ntut.csie.selab.entity.model.board.event.BoardCreated;
import ntut.csie.selab.entity.model.board.event.BoardCursorMoved;
import ntut.csie.selab.entity.model.board.event.BoardEntered;
import ntut.csie.selab.entity.model.widget.event.WidgetCreated;
import ntut.csie.selab.model.AggregateRoot;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Board extends AggregateRoot<String> {
    private String teamId;
    private String boardName;
    private List<CommittedWidget> committedWidgets;
    private Set<Cursor> cursorSet;

    public Board(String id, String teamId, String boardName) {
        super(id);
        this.teamId = teamId;
        this.boardName = boardName;
        this.committedWidgets = new ArrayList<>();
        this.cursorSet = new HashSet<>();

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

    public void setCommittedWidgets(List<CommittedWidget> committedWidgets) {
        this.committedWidgets = committedWidgets;
    }

    public Cursor getCursorBy(String userId) {
        return cursorSet.stream().filter(e -> e.getUserId().equals(userId)).findFirst().orElse(null);
    }

    public void setCursors(Set<Cursor> cursorSet) {
        this.cursorSet = cursorSet;
    }

    public void moveCursorOf(String userId, Point point) {
        Objects.requireNonNull(cursorSet.stream().filter(e -> e.getUserId().equals(userId)).findFirst().orElse(null)).setPoint(point);

        addDomainEvent(new BoardCursorMoved(new Date(), id, cursorSet));
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

    public void addCursor(Cursor cursor) {
        cursorSet.add(cursor);

        addDomainEvent(new BoardEntered(new Date(), id, cursorSet));
    }

    public void removeCursorBy(String userId) {
        cursorSet.removeIf(cursor -> cursor.getUserId().equals(userId));
    }

    public int getCursorCount() {
        return cursorSet.size();
    }

    public Set<Cursor> getCursors() {
        return cursorSet;
    }

}
