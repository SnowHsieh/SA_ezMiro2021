package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.board.create;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardInput;
import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardUseCase;
import ntut.csie.sslab.kanban.usecase.figure.sticker.create.CreateStickerInput;
import ntut.csie.sslab.kanban.usecase.figure.sticker.create.CreateStickerUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.ws.rs.QueryParam;
//
@RestController
@CrossOrigin
public class CreateStickerController {
    private CreateStickerUseCase createStickerUseCase;

    @Autowired
    public void setCreateStickerUseCase(CreateStickerUseCase createStickerUseCase) {
        this.createStickerUseCase = createStickerUseCase;
    }

    @PostMapping(path = "${KANBAN_PREFIX}/board/sticker/create", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel createSticker(@QueryParam("boardId") String boardId,
                                            @RequestBody String stickerInfo) {

        String content = "";
        int size = 0;
        String color = "";
        Coordinate position = null;
        try {
            JSONObject stickerJSON = new JSONObject(stickerInfo);
            content = stickerJSON.getString("content");
            size = stickerJSON.getInt("size");
            color = stickerJSON.getString("color");
            position = new Coordinate(stickerJSON.getLong("x"), stickerJSON.getLong("y"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        CreateStickerInput input = createStickerUseCase.newInput();
        input.setBoardId(boardId);
        input.setContent(content);
        input.setSize(size);
        input.setColor(color);
        input.setPosition(position);

        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        createStickerUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}

