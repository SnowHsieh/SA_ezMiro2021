//package ntut.csie.islab.miro.adapter.controller.rest.springboot.board.userenter;
//
//import ntut.csie.islab.miro.adapter.presenter.getContent.GetBoardContentPresenter;
//import ntut.csie.islab.miro.adapter.presenter.getContent.BoardContentViewModel;
//import ntut.csie.islab.miro.usecase.board.enterboard.EnterBoardInput;
//import ntut.csie.islab.miro.usecase.board.enterboard.EnterBoardUseCase;
//import ntut.csie.islab.miro.usecase.board.getboardcontent.GetBoardContentInput;
//import ntut.csie.islab.miro.usecase.board.getboardcontent.GetBoardContentUseCase;
//import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
//import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandViewModel;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@CrossOrigin
//public class EnterBoardController {
//    private EnterBoardUseCase enterBoardUseCase;
//    @Autowired
//    public void setEnterBoardUseCase(EnterBoardUseCase enterBoardUseCase) {
//        this.enterBoardUseCase = enterBoardUseCase;
//    }
//
//    @GetMapping(path = "/boards/{boardId}/enterBoard", produces = "application/json")
//    public CqrsCommandViewModel enterBoard(
//            @PathVariable("boardId") String boardId,
//            @RequestBody String UserInfo) {
//        UUID userId = null;
//
//        try {
//            JSONObject stickyNoteJSON = new JSONObject(UserInfo);
//            userId = UUID.fromString(stickyNoteJSON.getString("userId"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        EnterBoardInput input = enterBoardUseCase.newInput();
//        input.setBoardId(UUID.fromString(boardId));
//        input.setUserId(userId);
//
//        CqrsCommandPresenter presenter = CqrsCommandPresenter.newInstance();
//        enterBoardUseCase.execute(input, presenter);
//        return presenter.buildViewModel();
//
//    }
//
//}
