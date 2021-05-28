package ntut.csie.islab.miro.adapter.controller.rest.springboot.stickynote.changecolor;

import ntut.csie.islab.miro.usecase.textfigure.stickynote.changecolor.ChangeStickyNoteColorInput;
import ntut.csie.islab.miro.usecase.textfigure.stickynote.changecolor.ChangeStickyNoteColorUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class ChangeStickyNoteColorController {
    private ChangeStickyNoteColorUseCase changeStickyNoteColorUseCase;

    @Autowired
    public void setChangeStickyNoteColorUseCase(ChangeStickyNoteColorUseCase changeStickyNoteColorUseCase){
        this.changeStickyNoteColorUseCase = changeStickyNoteColorUseCase;
    }
    @PostMapping(path = "/board/{boardId}/changeStickyNoteColor", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeStickyNoteContent(
            @PathVariable("boardId") UUID boardId,
            @RequestBody String stickyNoteInfo){
        UUID figureId = null;
        String color = "";

        try {
            JSONObject stickyNoteJSON = new JSONObject(stickyNoteInfo);
            figureId = UUID.fromString(stickyNoteJSON.getString("figureId"));
            color = stickyNoteJSON.getString("color");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ChangeStickyNoteColorInput input = changeStickyNoteColorUseCase.newInput();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setColor(color);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeStickyNoteColorUseCase.execute(input, presenter);
        return presenter.buildViewModel();


    }




}
