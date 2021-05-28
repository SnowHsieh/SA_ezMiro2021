package ntut.csie.team5.adapter.controller.rest.springboot.board.getcontent;

import ntut.csie.team5.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.team5.adapter.presenter.board.getcontent.GetBoardContentPresenter;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentInput;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "${CORS_URL}")
@RestController
public class GetBoardContentController {

    private GetBoardContentUseCase getBoardContentUseCase;

    @Autowired
    public void setGetBoardContentUseCase(GetBoardContentUseCase getBoardContentUseCase) {
        this.getBoardContentUseCase = getBoardContentUseCase;
    }

    @GetMapping(path = "/boards/{boardId}/content", produces = "application/json")
    public BoardContentViewModel getBoardContent(@PathVariable("boardId") String boardId) {

        GetBoardContentInput input = (GetBoardContentInput) getBoardContentUseCase;
        GetBoardContentPresenter presenter = new GetBoardContentPresenter();

        input.setBoardId(boardId);

        getBoardContentUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
