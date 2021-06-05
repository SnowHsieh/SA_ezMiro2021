package ntut.csie.islab.miro.adapter.controller.rest.springboot.figure.line.attachfigure;


import ntut.csie.islab.miro.usecase.figure.line.attachtextfigure.AttachTextfigureInput;
import ntut.csie.islab.miro.usecase.figure.line.attachtextfigure.AttachTextfigureUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@CrossOrigin
public class AttachTextfigureController {
    private AttachTextfigureUseCase attachTextfigureUseCase;

    @Autowired
    public void setAttachTextfigureUseCase(AttachTextfigureUseCase attachTextfigureUseCase) {
        this.attachTextfigureUseCase = attachTextfigureUseCase;
    }

    @PostMapping(path = "/board/{boardId}/attachTextfigure", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel attachTextfigure(@PathVariable("boardId") UUID boardId,
                                                 @RequestBody String lineInfo) {
        UUID figureId = null;
        UUID textFigureId = null;
        String attachEndPointKind ="";
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = UUID.fromString(lineJSON.getString("figureId"));
            textFigureId = UUID.fromString(lineJSON.getString("textFigureId"));
            attachEndPointKind = lineJSON.getString("attachEndPointKind");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("attachTextfigure:" + figureId  + ";"+ textFigureId+"; "+attachEndPointKind);
        AttachTextfigureInput input = attachTextfigureUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setTextFigureId(textFigureId);
        input.setAttachEndPointKind(attachEndPointKind);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        attachTextfigureUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}