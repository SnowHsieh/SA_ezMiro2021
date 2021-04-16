package ntut.csie.sslab.miro.adapter.presenter.lane.get;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.miro.usecase.lane.LaneDto;

public class LaneViewModel implements ViewModel {
    private LaneDto lane;

    public LaneDto getLane() {
        return lane;
    }

    public void setLane(LaneDto laneDto) {
        this.lane = laneDto;
    }
}
