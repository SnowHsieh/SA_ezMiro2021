package ntut.csie.islab.miro.adapter.controller.rest.springboot.stickyNote.resize;

import ntut.csie.islab.miro.usecase.textFigure.stickyNote.ChangeStickyNoteColorInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.ResizeStickyNoteInput;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.ResizeStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(value = "http://localhost:8080")
public class ResizeStickyNoteController {
    private ResizeStickyNoteUseCase resizeStickyNoteUseCase;
    @Autowired
    public void setResizeStickyNoteUseCase(ResizeStickyNoteUseCase resizeStickyNoteUseCase){
        this.resizeStickyNoteUseCase =  resizeStickyNoteUseCase;
    }
    @PostMapping(path = "/board/{boardId}/resizeStickyNote", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeStickyNoteContent(
            @PathVariable("boardId") UUID boardId,
            @RequestBody String stickyNoteInfo){
        UUID figureId = null;
        Double width = 0.0;
        Double height = 0.0;

        try {
            JSONObject stickyNoteJSON = new JSONObject(stickyNoteInfo);
            figureId = UUID.fromString(stickyNoteJSON.getString("figureId"));
            width = stickyNoteJSON.getDouble("width");
            height = stickyNoteJSON.getDouble("height");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ResizeStickyNoteInput input = resizeStickyNoteUseCase.newInput();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setWidth(width);
        input.setHeight(height);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        resizeStickyNoteUseCase.execute(input, presenter);
        return presenter.buildViewModel();


    }


}
