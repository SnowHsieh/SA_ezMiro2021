package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.figure.sticker.changecolor;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecolor.ChangeStickerColorInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecolor.ChangeStickerColorUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@RestController
@CrossOrigin
public class ChangeStickerColorController {
    private ChangeStickerColorUseCase changeStickerColorUseCase;

    @Autowired
    public void setChangeStickerColorUseCase(ChangeStickerColorUseCase changeStickerColorUseCase) {
        this.changeStickerColorUseCase = changeStickerColorUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/sticker/changecolor", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeStickerColor(@QueryParam("stickerId") String stickerId,
                                                     @RequestBody String stickerInfo) {

        String color = "";
        try {
            JSONObject stickerJSON = new JSONObject(stickerInfo);
            color = stickerJSON.getString("color");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeStickerColorInput input = changeStickerColorUseCase.newInput();
        input.setFigureId(stickerId);
        input.setColor(color);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        changeStickerColorUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
