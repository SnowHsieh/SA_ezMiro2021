package ntut.csie.team5.adapter.controller.rest.springboot.board.create;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.board.create.CreateBoardInput;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class CreateBoardController {

    private CreateBoardUseCase createBoardUseCase;

    @Autowired
    public void setCreateBoardUseCase(CreateBoardUseCase createBoardUseCase) {
        this.createBoardUseCase = createBoardUseCase;
    }

    @PostMapping(path = "/projects/{projectId}/boards", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createBoard(@PathVariable("projectId") String projectId,
                                            @RequestBody String boardInfo) {
        String name = "";
        try {
            JSONObject boardJSON = new JSONObject(boardInfo);
            name = boardJSON.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CreateBoardInput input = createBoardUseCase.newInput();
        input.setBoardName(name);
        input.setProjectId(projectId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createBoardUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
