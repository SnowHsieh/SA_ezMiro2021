package ntut.csie.sslab.kanban.entity.model.workflow.event;


import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class LaneWipChanged extends DomainEvent {

    private final String laneId;
    private final int originWipLimit;
    private final int newWipLimit;

    public LaneWipChanged(String laneId, int originWipLimit, int newWipLimit) {
        super(DateProvider.now());
        this.laneId = laneId;
        this.originWipLimit = originWipLimit;
        this.newWipLimit = newWipLimit;
    }

    public String laneId() {
        return laneId;
    }

    public int originWipLimit() {
        return originWipLimit;
    }

    public int newWipLimit() {
        return newWipLimit;
    }
}