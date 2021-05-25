package ntut.csie.team5.adapter.controller.rest.springboot.figure.connectable_figure.note.post;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteInput;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class PostNoteController {

    private PostNoteUseCase postNoteUseCase;

    @Autowired
    public void setPostNoteUseCase(PostNoteUseCase postNoteUseCase) {
        this.postNoteUseCase = postNoteUseCase;
    }

    @PostMapping(path = "/post-note", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel postNote(@RequestBody String noteInfo) {
        String boardId = "";
        int leftTopPositionX = 0;
        int leftTopPositionY = 0;
        int height = 0;
        int width = 0;
        String color = "#000000";
        try {
            JSONObject noteJSON = new JSONObject(noteInfo);
            boardId = noteJSON.getString("boardId");
            leftTopPositionX = noteJSON.getInt("leftTopPositionX");
            leftTopPositionY = noteJSON.getInt("leftTopPositionY");
            height = noteJSON.getInt("height");
            width = noteJSON.getInt("width");
            color = noteJSON.getString("color");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PostNoteInput input = postNoteUseCase.newInput();
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setLeftTopPositionX(leftTopPositionX);
        input.setLeftTopPositionY(leftTopPositionY);
        input.setHeight(height);
        input.setWidth(width);
        input.setColor(color);
        input.setFigureType(FigureType.NOTE);

        postNoteUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
