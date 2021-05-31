package ntut.csie.islab.miro.adapter.controller.rest.springboot.figure.line.delete;

import ntut.csie.islab.miro.usecase.figure.line.delete.DeleteLineInput;
import ntut.csie.islab.miro.usecase.figure.line.delete.DeleteLineUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class DeleteLineController {
    private DeleteLineUseCase deleteLineUseCase;

    @Autowired
    public void setDeleteLineUseCase(DeleteLineUseCase deleteLineUseCase) {
        this.deleteLineUseCase = deleteLineUseCase;
    }

    @PostMapping(path = "/board/{boardId}/deleteLine", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel deleteLine(@PathVariable("boardId") UUID boardId,
                                                 @RequestBody String lineInfo) {
        UUID figureId = null;
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = UUID.fromString(lineJSON.getString("figureId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DeleteLineInput input = deleteLineUseCase.newInput();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        deleteLineUseCase.execute(input, presenter);
        return presenter.buildViewModel();

    }
}
