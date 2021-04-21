package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board;


import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BoardRepositoryImpl implements BoardRepository {

    private List<Board> boards;

    public BoardRepositoryImpl() {
        boards = new ArrayList<>();
    }

    @Override
    public Optional<Board> getBoardById(String boardId) {
        return boards.stream().filter(x-> x.getBoardId() == boardId).findFirst();
    }


    @Override
    public List<Board> findAll() {
        return Collections.unmodifiableList(boards);
    }

    @Override
    public Optional<Board> findById(String s) {
        return Optional.empty();
    }

    @Override
    public void save(Board board) {
        boards.add(board);
    }

    @Override
    public void deleteById(String s) {

    }
}
