package ntut.csie.sslab.miro.adapter.controller.rest.springboot.line.delete;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineInput;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.QueryParam;

@RestController
public class DeleteLineController {
    private DeleteLineUseCase deleteLineUseCase;

    @Autowired
    public void setDeleteLineUseCase(DeleteLineUseCase deleteLineUseCase) {
        this.deleteLineUseCase = deleteLineUseCase;
    }

    @PostMapping(path = "${MIRO_PREFIX}/lines/{lineId}/delete", produces = "application/json")
    public CqrsCommandViewModel deleteLine(@PathVariable("lineId") String lineId,
                                           @QueryParam("boardId") String boardId) {
        DeleteLineInput input = deleteLineUseCase.newInput();
        input.setLineId(lineId);
        input.setBoardId(boardId);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        deleteLineUseCase.execute(input, presenter);

        return presenter.buildViewModel();
    }
}