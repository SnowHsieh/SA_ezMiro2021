package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.figure.sticker.changesize;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecontent.ChangeStickerContentInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changecontent.ChangeStickerContentUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changesize.ChangeStickerSizeInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.changesize.ChangeStickerSizeUseCase;
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
    public void setChangeStickerContentUseCase(ChangeStickerSizeUseCase changeStickerSizeUseCase) {
        this.changeStickerSizeUseCase = changeStickerSizeUseCase;
    }

    @PutMapping(path = "${MIRO_PREFIX}/board/sticker/changesize", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeStickerSize(@QueryParam("stickerId") String stickerId,
                                                     @RequestBody String stickerInfo) {

        int size = 0;
        try {
            JSONObject stickerJSON = new JSONObject(stickerInfo);
            size = stickerJSON.getInt("size");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ChangeStickerSizeInput input = changeStickerSizeUseCase.newInput();
        input.setFigureId(stickerId);
        input.setSize(size);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        changeStickerSizeUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}

