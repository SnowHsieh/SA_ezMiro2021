package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.figure.sendtoback;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.figure.sendtoback.SendFigureToBackInput;
import ntut.csie.sslab.kanban.usecase.figure.sendtoback.SendFigureToBackUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
@CrossOrigin
public class SendFigureToBackController {
    private SendFigureToBackUseCase sendFigureToBackUseCase;

    @Autowired
    public void setSendFigureToBackUseCase(SendFigureToBackUseCase sendFigureToBackUseCase) {
        this.sendFigureToBackUseCase = sendFigureToBackUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/{boardId}/figure/sendtoback")
    public CqrsCommandViewModel sendStickerToBack(@QueryParam("figureId") String figureId,
                                                    @PathVariable("boardId") String boardId) {

        SendFigureToBackInput input = sendFigureToBackUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        sendFigureToBackUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
