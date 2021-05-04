package ntut.csie.selab.adapter.controller.rest.springboot.board.create;

import ntut.csie.selab.usecase.board.create.CreateBoardInput;
import ntut.csie.selab.usecase.board.create.CreateBoardOutput;
import ntut.csie.selab.usecase.board.create.CreateBoardUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class CreateBoardController {
    private CreateBoardUseCase createBoardUseCase;

    @Autowired
    public CreateBoardController(CreateBoardUseCase createBoardUseCase) {
        this.createBoardUseCase = createBoardUseCase;
    }

    @PostMapping(path = "/ez-miro/boards/", produces = "application/json")
    public String createBoard() {
        CreateBoardInput input = new CreateBoardInput();
        CreateBoardOutput output = new CreateBoardOutput();
        input.setTeamId("1");
        input.setBoardName("firstBoard");
        createBoardUseCase.execute(input, output);
        return output.getBoardId();
    }


}
