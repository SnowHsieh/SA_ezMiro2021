package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;


import ntut.csie.sslab.kanban.entity.model.workflow.LaneLayout;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="lane")
public class LaneData {

    @Id
    @Column(name="lane_id")
    private String id;

    @Column(name="workflow_id")
    private String workflowId;

    //do not use cascade={CascadeType.ALL}
    @ManyToOne(fetch=FetchType.EAGER)
    private LaneData parent;

    @Column(name="lane_name")
    private String name;

    @Column(name="wip_limit")
    private int wipLimit;

    @Column(name="lane_order")
    private int order;

    @Column(name="lane_type")
    private String type;

    @Column(name="lane_layout")
    private String layout;

    @OneToMany(
            mappedBy = "parent",
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @OrderBy("order")
    private final Set<LaneData> children;

    @OneToMany(mappedBy = "committedCardDataId.laneId", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CommittedCardData> committedCardData;

    public LaneData(){
        children = new HashSet<>();
        committedCardData = new HashSet<>();
    }

    public LaneData(String id, String workflowId, LaneData parent, String name, int wipLimit, int order, String type, String layout) {
        this();
        this.id = id;
        this.workflowId = workflowId;
        this.parent = parent;
        this.name = name;
        this.wipLimit = wipLimit;
        this.order = order;
        this.type = type;
        this.layout = layout;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public LaneData getParent() {
        return parent;
    }

    public void setParent(LaneData parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public int getWipLimit() {
        return wipLimit;
    }

    public void setWipLimit(int wipLimit) {
        this.wipLimit = wipLimit;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLayout() {
        return layout;
    }

    public boolean isStage() {
        return layout.equals(LaneLayout.Vertical.toString());
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public List<LaneData> getChildren() {
        return new ArrayList<>(children);
    }

    public void addChild(LaneData laneData){
        this.children.add(laneData);
    }


    public List<CommittedCardData> getCommittedCardData() {
        return new ArrayList<>(committedCardData);
    }


    public void addCommittedCardData(CommittedCardData committedCardData) {
        this.committedCardData.add(committedCardData);
    }

}
