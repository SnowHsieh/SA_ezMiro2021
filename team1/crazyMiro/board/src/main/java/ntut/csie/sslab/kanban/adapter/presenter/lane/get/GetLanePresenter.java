package ntut.csie.sslab.kanban.adapter.presenter.lane.get;

import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Result;
import ntut.csie.sslab.kanban.usecase.lane.LaneDto;
import ntut.csie.sslab.kanban.usecase.lane.get.GetLaneOutput;

public class GetLanePresenter extends Result implements Presenter<LaneViewModel>, GetLaneOutput {

    private LaneViewModel viewModel;

    public GetLanePresenter() {
        this.viewModel = new LaneViewModel();
    }

    @Override
    public LaneViewModel buildViewModel() {
        return viewModel;
    }

    @Override
    public LaneDto getLaneModel() {
        return viewModel.getLane();
    }

    @Override
    public void setLaneModel(LaneDto laneDto) {
        viewModel.setLane(laneDto);
    }

}
