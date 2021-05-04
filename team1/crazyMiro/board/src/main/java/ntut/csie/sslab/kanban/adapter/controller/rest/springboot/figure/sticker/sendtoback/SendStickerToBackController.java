package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.figure.sticker.sendtoback;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.figure.sticker.bringtofront.BringStickerToFrontInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.bringtofront.BringStickerToFrontUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.sendtoback.SendStickerToBackInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.sendtoback.SendStickerToBackUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
@CrossOrigin
public class SendStickerToBackController {
    private SendStickerToBackUseCase sendStickerToBackUseCase;

    @Autowired
    public void setSendStickerToBackUseCase(SendStickerToBackUseCase sendStickerToBackUseCase) {
        this.sendStickerToBackUseCase = sendStickerToBackUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/{boardId}/sticker/sendtoback")
    public CqrsCommandViewModel sendStickerToBack(@QueryParam("stickerId") String stickerId,
                                                    @PathVariable("boardId") String boardId) {

        SendStickerToBackInput input = sendStickerToBackUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(stickerId);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        sendStickerToBackUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
