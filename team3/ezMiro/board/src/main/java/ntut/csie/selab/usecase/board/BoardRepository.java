package ntut.csie.selab.usecase.board;


import ntut.csie.selab.entity.model.board.Board;

import java.util.Optional;

public interface BoardRepository {
    void add(Board board);

    Optional<Board> findById(String boardId);
}
