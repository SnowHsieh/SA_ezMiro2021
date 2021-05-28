package ntut.csie.selab.adapter.board;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.usecase.board.BoardRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardRepositoryInMemoryImpl implements BoardRepository {

    private List<Board> boards;

    public BoardRepositoryInMemoryImpl() {
        boards = new ArrayList<>();
    }

    @Override
    public void save(Board board) {
        if (!boards.contains(board)) {
            boards.add(board);
        }
    }

    @Override
    public Optional<Board> findById(final String boardId) {
        return boards.stream().filter(e -> e.getId().equals(boardId)).findFirst();
    }
}
