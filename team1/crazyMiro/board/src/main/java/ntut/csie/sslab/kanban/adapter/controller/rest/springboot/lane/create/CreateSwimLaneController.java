package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.lane.create;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.lane.swimLane.create.CreateSwimLaneInput;
import ntut.csie.sslab.kanban.usecase.lane.swimLane.create.CreateSwimLaneUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
public class CreateSwimLaneController {
    private CreateSwimLaneUseCase createSwimLaneUseCase;

    @Autowired
    public void setCreateSwimLaneUseCase(CreateSwimLaneUseCase createSwimLaneUseCase) {
        this.createSwimLaneUseCase = createSwimLaneUseCase;
    }

    @PostMapping(path = "${KANBAN_PREFIX}/workflows/{workflowId}/swimlanes", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createSwimLane(@PathVariable("workflowId") String workflowId,
                                               @QueryParam("userId") String userId,
                                               @QueryParam("username") String username,
                                               @QueryParam("boardId") String boardId,
                                               @RequestBody String swimLaneInfo) {


        String name = "";
        int wipLimit = -1;
        String laneType = "";
        String parentId = "";


        try {
            JSONObject swimLaneJSON = new JSONObject(swimLaneInfo);
            name = swimLaneJSON.getString("name");
            wipLimit= swimLaneJSON.getInt("wipLimit");
            laneType= swimLaneJSON.getString("laneType");
            parentId= swimLaneJSON.getString("parentId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CreateSwimLaneInput input = createSwimLaneUseCase.newInput();
        input.setWorkflowId(workflowId);
        input.setParentId(parentId);
        input.setName(name);
        input.setWipLimit(wipLimit);
        input.setLaneType(laneType);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createSwimLaneUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
