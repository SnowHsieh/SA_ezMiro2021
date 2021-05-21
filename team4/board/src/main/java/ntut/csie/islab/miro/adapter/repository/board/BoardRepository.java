package ntut.csie.islab.miro.adapter.repository.board;

import ntut.csie.islab.miro.entity.model.board.Board;


import java.util.*;


public interface BoardRepository {
    public Optional<Board> findById(UUID boardId);
    public void save(Board board);
}
