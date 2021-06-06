package ntut.csie.sslab.miro.adapter.controller.rest.springboot.command.figure.sticker.changesize;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.figure.sticker.changesize.ChangeStickerSizeInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.changesize.ChangeStickerSizeUseCase;
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
public class ChangeStickerSizeController {
    private ChangeStickerSizeUseCase changeStickerSizeUseCase;

    @Autowired
    public void setChangeStickerSizeUseCase(ChangeStickerSizeUseCase changeStickerSizeUseCase) {
        this.changeStickerSizeUseCase = changeStickerSizeUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/sticker/changesize", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeStickerSize(@QueryParam("stickerId") String stickerId,
                                                     @RequestBody String stickerInfo) {

        int width = 0;
        int length = 0;
        try {
            JSONObject stickerJSON = new JSONObject(stickerInfo);
            width = stickerJSON.getInt("width");
            length = stickerJSON.getInt("length");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeStickerSizeInput input = changeStickerSizeUseCase.newInput();
        input.setFigureId(stickerId);
        input.setWidth(width);
        input.setLength(length);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        changeStickerSizeUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}

