package ntut.csie.islab.miro.adapter.controller.rest.springboot.figure.line.unattachfigure;


import ntut.csie.islab.miro.usecase.figure.line.unattachconnectablefigure.UnattachConnectableFigureInput;
import ntut.csie.islab.miro.usecase.figure.line.unattachconnectablefigure.UnattachConnectableFigureUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
public class UnattachConnectableFigureController {
    private UnattachConnectableFigureUseCase unattachConnectableFigureUseCase;

    @Autowired
    public void setAttachConnectableFigureUseCase(UnattachConnectableFigureUseCase unattachConnectableFigureUseCase) {
        this.unattachConnectableFigureUseCase = unattachConnectableFigureUseCase;
    }

    @PostMapping(path = "/board/{boardId}/unattachConnectableFigure", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel attachConnectableFigure(@PathVariable("boardId") UUID boardId,
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
        UnattachConnectableFigureInput input = unattachConnectableFigureUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setAttachEndPointKind(attachEndPointKind);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        unattachConnectableFigureUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}