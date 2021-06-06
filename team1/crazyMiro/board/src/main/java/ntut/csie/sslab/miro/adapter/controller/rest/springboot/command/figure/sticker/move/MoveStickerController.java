package ntut.csie.sslab.miro.adapter.controller.rest.springboot.command.figure.sticker.move;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.figure.sticker.move.MoveStickerInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.move.MoveStickerUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MoveStickerController {
    private MoveStickerUseCase moveStickerUseCase;

    @Autowired
    public void setMoveStickerUseCase(MoveStickerUseCase moveStickerUseCase) {
        this.moveStickerUseCase = moveStickerUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/sticker/move", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel moveSticker(@RequestParam("stickerId") String stickerId,
                                                     @RequestParam("userId") String userId,
                                                     @RequestBody String stickerInfo) {

        Coordinate position = null;
        try {
            JSONObject stickerJSON = new JSONObject(stickerInfo);
            Long x = stickerJSON.getJSONObject("position").getLong("x");
            Long y = stickerJSON.getJSONObject("position").getLong("y");
            position = new Coordinate(x, y);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MoveStickerInput input = moveStickerUseCase.newInput();
        input.setFigureId(stickerId);
        input.setPosition(position);
        input.setUserId(userId);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        moveStickerUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}

