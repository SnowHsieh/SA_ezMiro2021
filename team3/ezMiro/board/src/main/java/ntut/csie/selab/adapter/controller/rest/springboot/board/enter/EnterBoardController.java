package ntut.csie.selab.adapter.controller.rest.springboot.board.enter;

import ntut.csie.selab.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.selab.adapter.websocket.WebSocketUtil;
import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.usecase.board.enterboard.EnterBoardInput;
import ntut.csie.selab.usecase.board.enterboard.EnterBoardOutput;
import ntut.csie.selab.usecase.board.enterboard.EnterBoardUseCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class EnterBoardController {
    private EnterBoardUseCase enterBoardUseCase;

    @Autowired
    public EnterBoardController(EnterBoardUseCase enterBoardUseCase) {
        this.enterBoardUseCase = enterBoardUseCase;
    }

    @PostMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}", produces = "application/json")
    public void enterBoard(@PathVariable("boardId") String boardId,
                                @RequestBody String userInfo) {
        String userId = "";
        try {
            JSONObject userInfoJSON = new JSONObject(userInfo);
            userId = userInfoJSON.getString("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EnterBoardInput input = new EnterBoardInput();
        EnterBoardOutput output = new EnterBoardOutput();
        input.setBoardId(boardId);
        input.setUserId(userId);
        enterBoardUseCase.execute(input, output);

        WebSocketUtil.sendMessageForAllUsersIn(boardId, convertCursorToMessage(output.getCursor()));
    }

    private JSONObject convertCursorToMessage(Set<Cursor> cursorSet) {
        JSONArray parsedCursors = new JSONArray();
        cursorSet.forEach(cursor -> {
            JSONObject parsedCursor = new JSONObject();
            try {
                parsedCursor.put("userId", cursor.getUserId());
                parsedCursor.put("x", cursor.getPoint().x);
                parsedCursor.put("y", cursor.getPoint().y);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            parsedCursors.put(parsedCursor);
        });

        JSONObject message = new JSONObject();
        try {
            message.put("cursors", parsedCursors);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

}
