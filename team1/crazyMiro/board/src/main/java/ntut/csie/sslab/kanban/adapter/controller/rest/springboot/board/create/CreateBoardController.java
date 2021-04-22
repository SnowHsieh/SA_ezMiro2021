//package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.board.create;
//
//import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
//import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
//import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardInput;
//import ntut.csie.sslab.kanban.usecase.board.create.CreateBoardUseCase;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.ws.rs.QueryParam;
//
//@RestController
//public class CreateBoardController {
//    private CreateBoardUseCase createBoardUseCase;
//
//    @Autowired
//    public void setCreateBoardUseCase(CreateBoardUseCase createBoardUseCase) {
//        this.createBoardUseCase = createBoardUseCase;
//    }
//
//    @PostMapping(path = "${KANBAN_PREFIX}/teams/{teamId}/boards", consumes = "application/json", produces = "application/json")
//    public CqrsCommandViewModel createBoard(@QueryParam("userId") String userId,
//                                            @PathVariable("teamId") String teamId,
//                                            @RequestBody String boardInfo) {
//
//        String name = "";
//        try {
//            JSONObject boardJSON = new JSONObject(boardInfo);
//            name = boardJSON.getString("name");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        CreateBoardInput input = createBoardUseCase.newInput();
//        input.setName(name);
//        input.setUserId(userId);
//        input.setTeamId(teamId);
//
//        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
//
//        createBoardUseCase.execute(input, presenter);
//        return presenter.buildViewModel();
//    }
//}
