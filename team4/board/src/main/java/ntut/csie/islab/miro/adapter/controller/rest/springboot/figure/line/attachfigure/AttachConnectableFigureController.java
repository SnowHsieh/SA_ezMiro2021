package ntut.csie.islab.miro.adapter.controller.rest.springboot.figure.line.attachfigure;


import ntut.csie.islab.miro.usecase.figure.line.attachconnectablefigure.AttachConnectableFigureInput;
import ntut.csie.islab.miro.usecase.figure.line.attachconnectablefigure.AttachConnectableFigureUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@CrossOrigin
public class AttachConnectableFigureController {
    private AttachConnectableFigureUseCase attachConnectableFigureUseCase;

    @Autowired
    public void setAttachConnectableFigureUseCase(AttachConnectableFigureUseCase attachConnectableFigureUseCase) {
        this.attachConnectableFigureUseCase = attachConnectableFigureUseCase;
    }

    @PostMapping(path = "/board/{boardId}/attachConnectableFigure", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel attachConnectableFigure(@PathVariable("boardId") UUID boardId,
                                                        @RequestBody String lineInfo) {
        UUID figureId = null;
        UUID connectableFigureId = null;
        String attachEndPointKind ="";
        try {
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = UUID.fromString(lineJSON.getString("figureId"));
            connectableFigureId = UUID.fromString(lineJSON.getString("connectableFigureId"));
            attachEndPointKind = lineJSON.getString("attachEndPointKind");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        AttachConnectableFigureInput input = attachConnectableFigureUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setConnectableFigureId(connectableFigureId);
        input.setAttachEndPointKind(attachEndPointKind);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        attachConnectableFigureUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}