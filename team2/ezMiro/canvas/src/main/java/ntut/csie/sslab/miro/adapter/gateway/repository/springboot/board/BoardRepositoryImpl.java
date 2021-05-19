package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;

import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardChannel;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardRepositoryImpl implements BoardRepository {
    private List<Board> boards = new ArrayList<>();

    public BoardRepositoryImpl() {
        Board board = new Board("team2", "boardId", "ezMiro", new BoardChannel("boardChannelId", "boardChannel"));
        boards.add(board);
    }

    @Override
    public List<Board> findAll() {
        return this.boards;
    }

    @Override
    public Optional<Board> findById(String boardId) {
        return boards.stream().filter(x -> x.getId().equals(boardId)).findFirst();
    }

    @Override
    public void save(Board data) {
        boards.add(data);
    }

    @Override
    public void deleteById(String s) {

    }
}