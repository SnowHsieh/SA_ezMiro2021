package ntut.csie.islab.miro.adapter.controller.rest.springboot.figure.line.changepath;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.usecase.figure.line.changepath.ChangeLinePathInput;
import ntut.csie.islab.miro.usecase.figure.line.changepath.ChangeLinePathUseCase;
import ntut.csie.islab.miro.usecase.figure.line.delete.DeleteLineInput;
import ntut.csie.islab.miro.usecase.figure.line.delete.DeleteLineUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class ChangeLinePathController {
    private ChangeLinePathUseCase changeLinePathUseCase;

    @Autowired
    public void setChangeLinePathUseCase(ChangeLinePathUseCase changeLinePathUseCase) {
        this.changeLinePathUseCase = changeLinePathUseCase;
    }

    @PostMapping(path = "/board/{boardId}/changeLinePath", consumes = "application/json", produces = "application/json")
    public CqrsCommandViewModel changeLinePath(@PathVariable("boardId") UUID boardId,
                                                 @RequestBody String lineInfo) {
        UUID figureId = null;
        List<Position> newPositionList = new ArrayList<>();
        try {
            System.out.println("lineInfo");
            System.out.println(lineInfo);
            JSONObject lineJSON = new JSONObject(lineInfo);
            figureId = UUID.fromString(lineJSON.getString("figureId"));
            JSONObject lineInfoJSON = new JSONObject(lineInfo);
            JSONArray jsonArray = lineInfoJSON.getJSONArray("positionList");

            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0;i<len;i++){
                    newPositionList.add(
                        new Position(
                        jsonArray.getJSONObject(i).getDouble("x"),
                        jsonArray.getJSONObject(i).getDouble("y")
                        )
                    );
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(newPositionList.get(0).getX() + ";" + newPositionList.get(0).getY());
        System.out.println(newPositionList.size());

        ChangeLinePathInput input = changeLinePathUseCase.newInput();

        input.setBoardId(boardId);
        input.setFigureId(figureId);
        input.setPositionList(newPositionList);
        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
        changeLinePathUseCase.execute(input, presenter);
        return presenter.buildViewModel();

    }
}
