package ntut.csie.sslab.kanban.entity.model.workflow;

import java.util.UUID;

public class LaneBuilder {

    private String id;
    private String workflowId;
    private Lane parent;
    private String name;
    private int order;
    private WipLimit wipLimit;
    private LaneType type;
    private LaneLayout laneLayout;

    public static LaneBuilder newInstance() {
        return new LaneBuilder();
    }

    public LaneBuilder workflowId(String workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public LaneBuilder parent(Lane parent) {
        this.parent = parent;
        return this;
    }

    public LaneBuilder name(String name) {
        this.name = name;
        return this;
    }

    public LaneBuilder type(LaneType type) {
        this.type = type;
        return this;
    }

    public LaneBuilder order(int order) {
        this.order = order;
        return this;
    }

    public LaneBuilder wipLimit(int wipLimit) {
        this.wipLimit = new WipLimit(wipLimit);
        return this;
    }

    public LaneBuilder stage() {
        this.laneLayout = LaneLayout.Vertical;
        return this;
    }

    public LaneBuilder swimLane() {
        this.laneLayout = LaneLayout.Horizontal;
        return this;
    }

    public Lane build() {
        Lane lane;
        id = UUID.randomUUID().toString();
        if(laneLayout == null){
            throw new RuntimeException("LaneLayout can not be null.");
        }else if(laneLayout == LaneLayout.Vertical){
            lane = new Stage(id, workflowId, parent, name, wipLimit, order, type);
        }else {
            lane = new SwimLane(id, workflowId, parent, name, wipLimit, order, type);
        }
        return lane;
    }

}
