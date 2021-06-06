package ntut.csie.sslab.miro.adapter.controller.rest.springboot.command.figure.sticker.delete;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.figure.sticker.delete.DeleteStickerInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.delete.DeleteStickerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
@CrossOrigin
public class DeleteStickerController {
    private DeleteStickerUseCase deleteStickerUseCase;

    @Autowired
    public void setDeleteStickerUseCase(DeleteStickerUseCase deleteStickerUseCase) {
        this.deleteStickerUseCase = deleteStickerUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/sticker/delete")
    public CqrsCommandViewModel deleteSticker(@QueryParam("stickerId") String stickerId) {

        DeleteStickerInput input = deleteStickerUseCase.newInput();
        input.setFigureId(stickerId);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        deleteStickerUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
