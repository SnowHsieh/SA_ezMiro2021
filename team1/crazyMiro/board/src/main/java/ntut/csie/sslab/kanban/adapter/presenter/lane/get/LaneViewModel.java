package ntut.csie.sslab.kanban.adapter.presenter.lane.get;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.kanban.usecase.lane.LaneDto;

public class LaneViewModel implements ViewModel {
    private LaneDto lane;

    public LaneDto getLane() {
        return lane;
    }

    public void setLane(LaneDto laneDto) {
        this.lane = laneDto;
    }
}
