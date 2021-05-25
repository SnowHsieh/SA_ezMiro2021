package ntut.csie.selab.adapter.gateway.repository.springboot.board;

import ntut.csie.selab.entity.model.board.Board;

import java.util.Optional;

public class BoardDataMapper {
    public static BoardData domainToData(Board board) {
        return new BoardData(board.getId(), board.getTeamId(), board.getBoardName());
    }

    public static Board DataToDomain(BoardData selectedBoardData) {
        return new Board(selectedBoardData.getBoardId(), selectedBoardData.getTeamId(), selectedBoardData.getBoardName());
    }
}
