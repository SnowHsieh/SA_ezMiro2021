package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.figure.bringtofront;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.figure.bringtofront.BringFigureToFrontInput;
import ntut.csie.sslab.kanban.usecase.figure.bringtofront.BringFigureToFrontUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
@CrossOrigin
public class BringFigureToFrontController {
    private BringFigureToFrontUseCase bringFigureToFrontUseCase;

    @Autowired
    public void setBringStickerToFrontUseCase(BringFigureToFrontUseCase bringFigureToFrontUseCase) {
        this.bringFigureToFrontUseCase = bringFigureToFrontUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/{boardId}/figure/bringtofront")
    public CqrsCommandViewModel bringStickerToFront(@QueryParam("figureId") String figureId,
                                                    @PathVariable("boardId") String boardId) {

        BringFigureToFrontInput input = bringFigureToFrontUseCase.newInput();
        input.setBoardId(boardId);
        input.setFigureId(figureId);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        bringFigureToFrontUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
