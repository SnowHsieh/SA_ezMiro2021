package ntut.csie.team5.adapter.controller.rest.springboot.figure.line.delete;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.usecase.figure.line.delete.DeleteLineInput;
import ntut.csie.team5.usecase.figure.line.delete.DeleteLineUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class DeleteLineController {

    private DeleteLineUseCase deleteLineUseCase;

    public void setDeleteLineUseCase(DeleteLineUseCase deleteLineUseCase) {
        this.deleteLineUseCase = deleteLineUseCase;
    }

    @PostMapping(path = "/delete-line", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel deleteLine(@RequestBody String lineInfo) {
        String figureId = "";

        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = lineJSON.getString("figureId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DeleteLineInput input = deleteLineUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setFigureId(figureId);

        deleteLineUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
