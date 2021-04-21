package ntut.csie.selab.adapter.board;

import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.entity.model.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardRepositoryImpl implements BoardRepository {

    private List<Board> boards;

    public BoardRepositoryImpl() {
        boards = new ArrayList<>();
    }

    @Override
    public void add(Board board) {
        boards.add(board);
    }

    @Override
    public Optional<Board> findById(final String boardId) {
        return boards.stream().filter(e -> e.getId().equals(boardId)).findFirst();
    }
}
