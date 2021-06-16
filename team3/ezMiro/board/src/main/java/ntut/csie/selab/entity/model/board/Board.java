package ntut.csie.selab.entity.model.board;

import ntut.csie.selab.entity.model.board.event.*;
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
    private Set<Cursor> cursors;

    public Board(String id, String teamId, String boardName) {
        super(id);
        this.teamId = teamId;
        this.boardName = boardName;
        this.committedWidgets = new ArrayList<>();
        this.cursors = new HashSet<>();
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
        sortAscendByZOrder(committedWidgets);
    }

    public void changeZOrder(int originZOrder, int newZOrder) {
        if (originZOrder < newZOrder) {
            shiftZOrderInRange(originZOrder, newZOrder, newZOrder, committedWidgets, -1);
        } else {
            shiftZOrderInRange(newZOrder, originZOrder, newZOrder, committedWidgets, 1);
        }
        sortAscendByZOrder(committedWidgets);
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
        addDomainEvent(new BoardCursorMoved(new Date(), new Cursor(id, userId, point)));
    }

    public Optional<CommittedWidget> getCommittedWidgetBy(String widgetId) {
        return committedWidgets.stream().filter(e -> e.getWidgetId().equals(widgetId)).findFirst();
    }

    public void commitWidgetCreation(String widgetId) {
        committedWidgets.add(new CommittedWidget(this.id, widgetId, committedWidgets.size()));
        addDomainEvent(new WidgetCreationCommitted(new Date()));
    }

    public void commitWidgetDeletion(String widgetId) {
        CommittedWidget removedCommittedWidget = getCommittedWidgetBy(widgetId).get();
        committedWidgets.remove(removedCommittedWidget);
        List<CommittedWidget> clone = new ArrayList<>(committedWidgets);
        committedWidgets.clear();
        for(int i = 0; i < clone.size(); i++) {
            int zOrder = i;
            committedWidgets.add(new CommittedWidget(getId(), clone.get(i).getWidgetId(), zOrder));
        }
        addDomainEvent(new WidgetDeletionCommitted(new Date()));
    }

    public void addCursor(Cursor cursor) {
        cursors.add(cursor);
        addDomainEvent(new BoardEntered(new Date(), id, cursor));
    }

    public void removeCursorBy(String userId) {
        addDomainEvent(new BoardLeft(new Date(), id, userId));
    }

}
