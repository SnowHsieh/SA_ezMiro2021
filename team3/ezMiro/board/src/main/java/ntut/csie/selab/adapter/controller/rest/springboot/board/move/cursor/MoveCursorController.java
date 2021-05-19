package ntut.csie.selab.adapter.controller.rest.springboot.board.move.cursor;

import ntut.csie.selab.adapter.websocket.WebSocketUtil;
import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.usecase.board.movecursor.MoveCursorInput;
import ntut.csie.selab.usecase.board.movecursor.MoveCursorOutput;
import ntut.csie.selab.usecase.board.movecursor.MoveCursorUseCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Set;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class MoveCursorController {
    private MoveCursorUseCase moveCursorUseCase;

    @Autowired
    public MoveCursorController(MoveCursorUseCase moveCursorUseCase) {
        this.moveCursorUseCase = moveCursorUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/cursor", produces = "application/json", consumes = "application/json")
    public void moveCursor(@PathVariable("boardId") String boardId,
                           @RequestBody String cursorInfo) {
        MoveCursorInput input = new MoveCursorInput();
        MoveCursorOutput output = new MoveCursorOutput();
        int cursorX = 0;
        int cursorY = 0;
        String userId = "";

        try {
            JSONObject cursorInfoJSON = new JSONObject(cursorInfo);
            cursorX = cursorInfoJSON.getInt("x");
            cursorY = cursorInfoJSON.getInt("y");
            userId = cursorInfoJSON.getString("userId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setPoint(new Point(cursorX, cursorY));
        moveCursorUseCase.execute(input, output);

        WebSocketUtil.sendMessageForAllUsersIn(boardId, convertCursorToMessage(output.getCursors()));
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
