package ntut.csie.sslab.miro.adapter.controller.rest.springboot.note.edit.size;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeInput;
import ntut.csie.sslab.miro.usecase.note.edit.size.ChangeNoteSizeUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangeNoteSizeController {
    private ChangeNoteSizeUseCase changeNoteSizeUseCase;

    @Autowired
    public void setChangeNoteSizeUseCase(ChangeNoteSizeUseCase changeNoteSizeUseCase) {
        this.changeNoteSizeUseCase = changeNoteSizeUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/notes/{noteId}/edit/size", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeNoteSize(@PathVariable("noteId") String noteId,
                                                @RequestBody String noteInfo) {
        double width = 0;
        double height = 0;

        try {
            JSONObject boardJSON = new JSONObject(noteInfo);
            width = boardJSON.getJSONObject("size").getDouble("width");
            height = boardJSON.getJSONObject("size").getDouble("height");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeNoteSizeInput input = changeNoteSizeUseCase.newInput();
        input.setNoteId(noteId);
        input.setWidth(width);
        input.setHeight(height);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeNoteSizeUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}