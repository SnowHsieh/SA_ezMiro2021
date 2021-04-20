package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.card.create;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.card.create.CreateCardInput;
import ntut.csie.sslab.kanban.usecase.card.create.CreateCardUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;

@RestController
public class CreateCardController {
	private CreateCardUseCase createCardUseCase;

	@Autowired
	public void setCreateCardUseCase(CreateCardUseCase createCardUseCase) {
		this.createCardUseCase = createCardUseCase;
	}

	@PostMapping(path = "${KANBAN_PREFIX}/cards", consumes = "application/json", produces = "application/json")
	public CqrsCommandViewModel createCard(@QueryParam("workflowId") String workflowId,
										   @QueryParam("laneId") String laneId,
										   @QueryParam("userId") String userId,
										   @QueryParam("username") String username,
										   @QueryParam("boardId") String boardId,
										   @RequestBody String cardInfo) {

		String description = "";
		String estimate = "";
		String note = "";
		String deadline = "";
		String type = "";

		try {
			JSONObject cardJSON = new JSONObject(cardInfo);
			description = cardJSON.getString("description");
			estimate = cardJSON.getString("estimate");
			note = cardJSON.getString("note");
			deadline = cardJSON.getString("deadline");
			type = cardJSON.getString("type");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		CreateCardInput input = createCardUseCase.newInput();
		input.setWorkflowId(workflowId);
		input.setLaneId(laneId);
		input.setUserId(userId);
		input.setDescription(description);
		input.setEstimate(estimate);
		input.setNote(note);
		input.setDeadline(deadline);
		input.setType(type);
		input.setUsername(username);
		input.setBoardId(boardId);

		CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

		createCardUseCase.execute(input, presenter);
		return presenter.buildViewModel();
	}
}
