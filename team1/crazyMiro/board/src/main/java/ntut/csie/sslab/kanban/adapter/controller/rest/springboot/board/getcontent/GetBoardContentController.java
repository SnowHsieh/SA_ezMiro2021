package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.board.getcontent;

import ntut.csie.sslab.kanban.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.sslab.kanban.adapter.presenter.board.getcontent.GetBoardContentPresenter;
import ntut.csie.sslab.kanban.usecase.board2.getcontent.GetBoardContentInput;
import ntut.csie.sslab.kanban.usecase.board2.getcontent.GetBoardContentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetBoardContentController {
    private GetBoardContentUseCase getBoardContentUseCase;

    @Autowired
    public void setGetBoardContentUseCase(GetBoardContentUseCase getBoardContentUseCase) {
        this.getBoardContentUseCase = getBoardContentUseCase;
    }

    @GetMapping(path = "${KANBAN_PREFIX}/boards/{boardId}/content", produces = "application/json")
    public BoardContentViewModel getBoardContent(@PathVariable("boardId") String boardId) {

        GetBoardContentInput input = (GetBoardContentInput) getBoardContentUseCase;
        input.setBoardId(boardId);

        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        getBoardContentUseCase.execute(input, presenter);
        return presenter.buildViewModel();
    }
}
