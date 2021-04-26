package ntut.csie.team5.board;

import ntut.csie.team5.entity.model.board.Board;
import ntut.csie.team5.usecase.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardRepositoryImpl implements BoardRepository {

    private List<Board> boards;

    public BoardRepositoryImpl(){
        boards = new ArrayList<>();
    }

    @Override
    public List<Board> getBoardsByProjectId(String projectId) {
        return null;
    }

    @Override
    public List<Board> findAll() {
        return null;
    }

    @Override
    public Optional<Board> findById(String id) {
        return boards.stream().filter(board -> board.getBoardId().equals(id)).findAny();
    }

    @Override
    public void save(Board board) {
        boards.add(board);
    }

    @Override
    public void deleteById(String id) {

    }
}
