package ntut.csie.selab.usecase.board;

import ntut.csie.selab.usecase.board.create.CreateBoardInput;
import ntut.csie.selab.usecase.board.create.CreateBoardOutput;
import ntut.csie.selab.usecase.board.entity.model.Board;

public class CreateBoardUseCase {

    public void execute(CreateBoardInput input, CreateBoardOutput output) {
        Board board = new Board(input.getTeamId(), input.getBoardName());

        output.setTeamId(board.getTeamId());
        output.setBoardName(board.getBoardName());
    }
}
