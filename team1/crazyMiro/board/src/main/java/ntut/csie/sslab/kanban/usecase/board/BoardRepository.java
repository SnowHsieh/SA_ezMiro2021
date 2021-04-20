package ntut.csie.sslab.kanban.usecase.board;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.kanban.entity.model.board.Board;

import java.util.Optional;

public interface BoardRepository extends AbstractRepository<Board, String> {
    Optional<Board> getBoardById(String boardId);


}
