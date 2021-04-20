package ntut.csie.sslab.kanban.usecase.lane.get;

import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.kanban.usecase.lane.LaneDto;

public interface GetLaneOutput extends Output {
    public LaneDto getLaneModel();

    public void setLaneModel(LaneDto laneDto);
}
