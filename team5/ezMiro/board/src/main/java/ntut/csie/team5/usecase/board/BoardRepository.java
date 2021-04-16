package ntut.csie.team5.usecase.board;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.team5.entity.model.board.Board;

import java.util.List;

public interface BoardRepository extends AbstractRepository<Board, String> {

    List<Board> getBoardsByProjectId(String projectId);

}
