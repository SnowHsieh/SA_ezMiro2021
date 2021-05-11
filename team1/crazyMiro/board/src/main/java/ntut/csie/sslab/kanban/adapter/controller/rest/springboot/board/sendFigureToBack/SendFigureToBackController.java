package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.board.sendFigureToBack;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.board.sendFigureToBack.SendFigureToBackInput;
import ntut.csie.sslab.kanban.usecase.board.sendFigureToBack.SendFigureToBackUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SendFigureToBackController {
    private SendFigureToBackUseCase sendFigureToBackUseCase;

    @Autowired
    public void setSendFigureToBackUseCase(SendFigureToBackUseCase sendFigureToBackUseCase) {
        this.sendFigureToBackUseCase = sendFigureToBackUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/{boardId}/figure/sendtoback")
    public CqrsCommandViewModel sendFigureToBack(@RequestParam("figureId") String figureId,
                                                   @PathVariable("boardId") String boardId) {


        SendFigureToBackInput input = sendFigureToBackUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        sendFigureToBackUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
