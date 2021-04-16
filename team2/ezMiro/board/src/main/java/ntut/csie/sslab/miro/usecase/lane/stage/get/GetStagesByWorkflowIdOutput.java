package ntut.csie.sslab.miro.usecase.lane.stage.get;

import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.miro.usecase.lane.LaneDto;

import java.util.List;

public interface GetStagesByWorkflowIdOutput extends Output {
    public List<LaneDto> getStageModels();

    public void setStageModels(List<LaneDto> stageModels);

}
