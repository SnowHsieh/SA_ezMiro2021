package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.figure.sticker.bringtofront;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.figure.sticker.bringtofront.BringStickerToFrontInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.bringtofront.BringStickerToFrontUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
@CrossOrigin
public class BringStickerToFrontController {
    private BringStickerToFrontUseCase bringStickerToFrontUseCase;

    @Autowired
    public void setBringStickerToFrontUseCase(BringStickerToFrontUseCase bringStickerToFrontUseCase) {
        this.bringStickerToFrontUseCase = bringStickerToFrontUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/{boardId}/sticker/bringtofront")
    public CqrsCommandViewModel bringStickerToFront(@QueryParam("stickerId") String stickerId,
                                                    @PathVariable("boardId") String boardId) {

        BringStickerToFrontInput input = bringStickerToFrontUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(stickerId);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        bringStickerToFrontUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
