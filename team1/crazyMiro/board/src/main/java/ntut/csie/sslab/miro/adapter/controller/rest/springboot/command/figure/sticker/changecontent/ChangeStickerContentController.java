package ntut.csie.sslab.miro.adapter.controller.rest.springboot.command.figure.sticker.changecontent;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.miro.usecase.figure.sticker.changecontent.ChangeStickerContentInput;
import ntut.csie.sslab.miro.usecase.figure.sticker.changecontent.ChangeStickerContentUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@CrossOrigin
public class ChangeStickerContentController {
    private ChangeStickerContentUseCase changeStickerContentUseCase;

    @Autowired
    public void setChangeStickerContentUseCase(ChangeStickerContentUseCase changeStickerContentUseCase) {
        this.changeStickerContentUseCase = changeStickerContentUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/sticker/changecontent", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeStickerContent(@QueryParam("stickerId") String stickerId,
                                              @RequestBody String stickerInfo) {

        String content = "";
        try {
            JSONObject stickerJSON = new JSONObject(stickerInfo);
            content = stickerJSON.getString("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeStickerContentInput input = changeStickerContentUseCase.newInput();
        input.setFigureId(stickerId);
        input.setContent(content);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        changeStickerContentUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
