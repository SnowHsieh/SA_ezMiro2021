package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.workflow;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="workflow")
public class WorkflowData {

    @Id
    @Column(name="workflow_id")
    private String id;

    @Column(name="board_id", nullable = false)
    private String boardId;

    @Column(name="workflow_name")
    private String name;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(name = "rootstage_in_workflow",
            joinColumns = {@JoinColumn(name = "workflow_id")},
            inverseJoinColumns = {@JoinColumn(name = "lane_id")})
    @OrderBy("order")
    private final Set<LaneData> laneDatas;


    public WorkflowData() {
        this.laneDatas = new HashSet<>();
    }

    public WorkflowData(String id, String boardId, String name) {
        this();
        this.id = id;
        this.boardId = boardId;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public List<LaneData> getLaneDatas() {
        return new ArrayList<>(laneDatas);
    }

    public void addLaneData(LaneData laneData){
        laneDatas.add(laneData);
    }
}
