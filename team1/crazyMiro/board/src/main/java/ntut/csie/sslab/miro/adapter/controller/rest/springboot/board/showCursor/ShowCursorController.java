package ntut.csie.sslab.miro.adapter.controller.rest.springboot.board.showCursor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.usecase.board.showCursor.ShowCursorInput;
import ntut.csie.sslab.miro.usecase.board.showCursor.ShowCursorUseCase;
import ntut.csie.sslab.miro.usecase.figure.sticker.move.MoveStickerInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.move.MoveStickerUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ShowCursorController {
    private ShowCursorUseCase showCursorUseCase;

    @Autowired
    public void setShowCursorUseCase(ShowCursorUseCase showCursorUseCase) {
        this.showCursorUseCase = showCursorUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/showcursor", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel showCursor(@RequestParam("boardId") String boardId,
                                            @RequestParam("userId") String userId,
                                            @RequestBody String cursorInfo) {

        Coordinate position = null;
        try {
            JSONObject cursorJSON = new JSONObject(cursorInfo);
            Long x = cursorJSON.getJSONObject("position").getLong("x");
            Long y = cursorJSON.getJSONObject("position").getLong("y");
            position = new Coordinate(x, y);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ShowCursorInput input = showCursorUseCase.newInput();
        input.setBoardId(boardId);
        input.setPosition(position);
        input.setUserId(userId);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        showCursorUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
