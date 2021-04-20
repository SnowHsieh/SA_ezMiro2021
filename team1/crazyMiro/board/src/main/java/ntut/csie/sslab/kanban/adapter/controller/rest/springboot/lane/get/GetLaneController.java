package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.lane.get;

import ntut.csie.sslab.kanban.adapter.presenter.lane.get.GetLanePresenter;
import ntut.csie.sslab.kanban.adapter.presenter.lane.get.LaneViewModel;
import ntut.csie.sslab.kanban.usecase.lane.get.GetLaneInput;
import ntut.csie.sslab.kanban.usecase.lane.get.GetLaneUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
public class GetLaneController {
    private GetLaneUseCase getLaneUseCase;

    @Autowired
    public void setGetLaneUseCase(GetLaneUseCase getLaneUseCase) {
        this.getLaneUseCase = getLaneUseCase;
    }

    @GetMapping(path = "${KANBAN_PREFIX}/workflows/{workflowId}/lanes/{laneId}", produces = "application/json")
    public LaneViewModel getLane(@PathVariable("workflowId") String workflowId,
                                 @PathVariable("laneId") String laneId,
                                 @QueryParam("boardId") String boardId) {

        GetLaneInput input = (GetLaneInput) getLaneUseCase;
        input.setWorkflowId(workflowId);
        input.setLaneId(laneId);

        GetLanePresenter presenter = new GetLanePresenter();

        getLaneUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }

}
