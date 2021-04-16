package ntut.csie.sslab.miro.usecase.lane.get;

import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.miro.usecase.lane.LaneDto;

public interface GetLaneOutput extends Output {
    public LaneDto getLaneModel();

    public void setLaneModel(LaneDto laneDto);
}
