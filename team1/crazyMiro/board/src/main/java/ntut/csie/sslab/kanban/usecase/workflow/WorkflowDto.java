package ntut.csie.sslab.kanban.usecase.workflow;

import ntut.csie.sslab.kanban.usecase.lane.LaneDto;

import java.util.List;

public class WorkflowDto {
    private String workflowId;
    private String boardId;
    private String name;
    private List<LaneDto> lanes;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
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

    public void setName(String name) {
        this.name = name;
    }

    public List<LaneDto> getLanes() {
        return lanes;
    }

    public void setLanes(List<LaneDto> lanes) {
        this.lanes = lanes;
    }
}
