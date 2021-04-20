package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.board.create;

import org.springframework.web.bind.annotation.*;

@RestController
public class CreateBoardController {
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
}
