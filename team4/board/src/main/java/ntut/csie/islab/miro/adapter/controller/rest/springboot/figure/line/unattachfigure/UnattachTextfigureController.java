package ntut.csie.islab.miro.adapter.controller.rest.springboot.figure.line.unattachfigure;


import ntut.csie.islab.miro.usecase.figure.line.attachtextfigure.AttachTextfigureInput;
import ntut.csie.islab.miro.usecase.figure.line.attachtextfigure.AttachTextfigureUseCase;
import ntut.csie.islab.miro.usecase.figure.line.unattachtextfigure.UnattachTextfigureInput;
import ntut.csie.islab.miro.usecase.figure.line.unattachtextfigure.UnattachTextfigureUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class UnattachTextfigureController {
    private UnattachTextfigureUseCase unattachTextfigureUseCase;

    @Autowired
    public void setAttachTextfigureUseCase(UnattachTextfigureUseCase unattachTextfigureUseCase) {
        this.unattachTextfigureUseCase = unattachTextfigureUseCase;
    }

    @PostMapping(path = "/board/{boardId}/unattachTextfigure", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel attachTextfigure(@PathVariable("boardId") UUID boardId,
                                                 @RequestBody String lineInfo) {
        UUID figureId = null;
        String attachEndPointKind ="";
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = UUID.fromString(lineJSON.getString("figureId"));
            attachEndPointKind = lineJSON.getString("attachEndPointKind");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        UnattachTextfigureInput input = unattachTextfigureUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setAttachEndPointKind(attachEndPointKind);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        unattachTextfigureUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}