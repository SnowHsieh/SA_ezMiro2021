package ntut.csie.selab.entity.model.board;

import ntut.csie.selab.entity.model.board.event.BoardCreated;
import ntut.csie.selab.entity.model.board.event.BoardCursorMoved;
import ntut.csie.selab.entity.model.board.event.BoardEntered;
import ntut.csie.selab.entity.model.widget.event.WidgetCreated;
import ntut.csie.selab.entity.model.widget.event.WidgetZOrderChanged;
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

    public void changeZOrder(int originZOrder, int newZOrder) {
        sortAscendByZOrder(committedWidgets);
        if (originZOrder < newZOrder) {
            shiftZOrderInRange(originZOrder, newZOrder, newZOrder, committedWidgets, -1);
        } else {
            shiftZOrderInRange(newZOrder, originZOrder, newZOrder, committedWidgets, 1);
        }
        addDomainEvent(new WidgetZOrderChanged(new Date(), id, committedWidgets.get(newZOrder).getWidgetId()));
    }

    private void sortAscendByZOrder(List<CommittedWidget> widgets) {
        widgets.sort((w1, w2) -> w1.getZOrder() - w2.getZOrder());
    }

    private void shiftZOrderInRange(int startIndex, int endIndex, int targetZOrder, List<CommittedWidget> committedWidgets, int offset) {
        List<CommittedWidget> subList = new ArrayList<>(committedWidgets.subList(startIndex, endIndex + 1));
        committedWidgets.removeAll(subList);
        CommittedWidget target;
        if (offset > 0) {
            target = subList.remove(subList.size() - 1);
        } else {
            target = subList.remove(0);
        }
        for (int i = 0; i < subList.size(); i++) {
            committedWidgets.add(startIndex + i, new CommittedWidget(subList.get(i).getBoardId(), subList.get(i).getWidgetId(), subList.get(i).getZOrder() + offset));
        }
        committedWidgets.add(endIndex, new CommittedWidget(target.getBoardId(), target.getWidgetId(), targetZOrder));
    }

    public void moveCursorOf(String userId, Point point) {
        cursorSet.remove(cursorSet.stream().filter(e -> e.getUserId().equals(userId)).findFirst().orElse(null));
        cursorSet.add(new Cursor(this.id, userId, point));
        addDomainEvent(new BoardCursorMoved(new Date(), id, cursorSet));
    }

    public Optional<CommittedWidget> getCommittedWidgetBy(String widgetId) {
        return committedWidgets.stream().filter(e -> e.getWidgetId().equals(widgetId)).findFirst();
    }

    public void commitWidgetCreation(String widgetId) {
        committedWidgets.add(new CommittedWidget(this.id, widgetId, committedWidgets.size()));
    }

    public void commitWidgetDeletion(String widgetId) {
        CommittedWidget removedCommittedWidget = getCommittedWidgetBy(widgetId).get();
        committedWidgets.remove(removedCommittedWidget);
        List<CommittedWidget> clone = new ArrayList<>();
        Collections.copy(clone, committedWidgets);
        committedWidgets.clear();
        for(int i = 0; i < clone.size(); i++) {
            int zOrder = i;
            if (clone.get(i).getZOrder() > removedCommittedWidget.getZOrder()) {
                zOrder--;
            }
            committedWidgets.add(new CommittedWidget(widgetId, getId(), zOrder));
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
