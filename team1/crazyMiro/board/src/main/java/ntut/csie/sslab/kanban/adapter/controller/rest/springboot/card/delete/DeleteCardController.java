package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.card.delete;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.card.delete.DeleteCardInput;
import ntut.csie.sslab.kanban.usecase.card.delete.DeleteCardUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;

@RestController
public class DeleteCardController {
	private DeleteCardUseCase deleteCardUseCase;

	@Autowired
	public void setDeleteCardUseCase(DeleteCardUseCase deleteCardUseCase) {
		this.deleteCardUseCase = deleteCardUseCase;
	}

	@DeleteMapping(path = "${KANBAN_PREFIX}/cards/{cardId}", produces = "application/json")
	public CqrsCommandViewModel deleteCard(@PathVariable("cardId") String cardId,
										   @QueryParam("workflowId") String workflowId,
										   @QueryParam("laneId") String laneId,
										   @QueryParam("userId") String userId,
										   @QueryParam("username") String username,
										   @QueryParam("boardId") String boardId) {

		DeleteCardInput input = deleteCardUseCase.newInput();
		input.setWorkflowId(workflowId);
		input.setLaneId(laneId);
		input.setCardId(cardId);
		input.setUserId(userId);
		input.setUsername(username);
		input.setBoardId(boardId);

		CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

		deleteCardUseCase.execute(input, presenter);
		return presenter.buildViewModel();
	}

}
