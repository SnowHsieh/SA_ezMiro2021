package ntut.csie.islab.miro.adapter.controller.rest.springboot.board.getcontent;

import ntut.csie.islab.miro.adapter.presenter.GetBoardContentPresenter;
import ntut.csie.islab.miro.adapter.presenter.getContent.BoardContentViewModel;
import ntut.csie.islab.miro.usecase.board.GetBoardContentUseCase;
import ntut.csie.islab.miro.usecase.board.GetBoardContentInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin(value = "http://localhost:8080")
public class GetBoardContentController {
    private GetBoardContentUseCase getBoardContentUseCase;

    @Autowired
    public void setGetBoardContentUseCase(GetBoardContentUseCase getBoardContentUseCase) {
        this.getBoardContentUseCase = getBoardContentUseCase;
    }

    @GetMapping(path = "/boards/{boardId}/content", produces = "application/json")
    public BoardContentViewModel getBoardContent(@PathVariable("boardId") String boardId) {
//        System.out.println("in /boards/{boardId}/content");
        //todo: Kanban getBoardContentUseCaseimpl implement the GetBoardContentInput and getBoardContentUseCase.
        GetBoardContentInput input = getBoardContentUseCase.newInput();
        input.setBoardId(UUID.fromString(boardId));
        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        getBoardContentUseCase.execute(input, presenter);
        return presenter.buildViewModel();

    }


}
