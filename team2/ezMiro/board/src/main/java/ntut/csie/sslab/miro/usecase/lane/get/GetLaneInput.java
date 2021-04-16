package ntut.csie.sslab.miro.usecase.lane.get;


import ntut.csie.sslab.ddd.usecase.Input;

public interface GetLaneInput extends Input {
    public String getWorkflowId();

    public void setWorkflowId(String workflowId);

    public String getLaneId();

    public void setLaneId(String laneId);
}
