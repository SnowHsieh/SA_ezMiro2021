package ntut.csie.selab.adapter.controller.rest.springboot.board.edit;

import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetInput;
import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetOutput;
import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetUseCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${CORS_URLS}")
public class ChangeZOrderOfStickyNoteController {
    private ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase;

    @Autowired
    public ChangeZOrderOfStickyNoteController(ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase) {
        this.changeZOrderOfWidgetUseCase = changeZOrderOfWidgetUseCase;
    }

    @PutMapping(path = "/${EZ_MIRO_PREFIX}/boards/{boardId}/widgets/sticky-notes/{widgetId}/z-order", consumes = "application/json", produces = "application/json")
    public String changeZOrderOfWidget(@PathVariable("boardId") String boardId,
                                       @PathVariable("widgetId") String widgetId,
                                       @RequestBody String zOrderInfo) {
        ChangeZOrderOfWidgetInput input = new ChangeZOrderOfWidgetInput();
        ChangeZOrderOfWidgetOutput output = new ChangeZOrderOfWidgetOutput();
        input.setWidgetId(widgetId);
        int zIndex = 0;
        try {
            JSONObject zIndexJson = new JSONObject(zOrderInfo);
            zIndex = zIndexJson.getInt("zOrder");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        input.setWidgetId(widgetId);
        input.setZOrder(zIndex);
        input.setBoardId(boardId);
        changeZOrderOfWidgetUseCase.execute(input, output);

        return output.getWidgetId();
    }
}
