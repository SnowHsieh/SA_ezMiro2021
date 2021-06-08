package ntut.csie.islab.miro.adapter.controller.rest.springboot.figure.connectablefigure.stickynote.changecontent;

import ntut.csie.islab.miro.usecase.figure.connectablefigure.stickynote.changecontent.ChangeStickyNoteContentInput;
import ntut.csie.islab.miro.usecase.figure.connectablefigure.stickynote.changecontent.ChangeStickyNoteContentUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class ChangeStickyNoteContentController {

    private ChangeStickyNoteContentUseCase changeStickyNoteContentUseCase;

    @Autowired
    public void setChangeStickyNoteContentUseCase(ChangeStickyNoteContentUseCase changeStickyNoteContentUseCase){
        this.changeStickyNoteContentUseCase = changeStickyNoteContentUseCase;
    }

    @PostMapping(path = "/board/{boardId}/changeStickyNoteContent", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeStickyNoteContent(
            @PathVariable("boardId") UUID boardId,
            @RequestBody String stickyNoteInfo) {
        UUID figureId = null;
        String content = "";

        try {
            JSONObject stickyNoteJSON = new JSONObject(stickyNoteInfo);
            figureId = UUID.fromString(stickyNoteJSON.getString("figureId"));
            content = stickyNoteJSON.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeStickyNoteContentInput input = changeStickyNoteContentUseCase.newInput();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setContent(content);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        changeStickyNoteContentUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
