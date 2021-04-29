package ntut.csie.islab.miro.adapter.repository.board;

import ntut.csie.islab.miro.entity.model.board.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BoardRepository {
    private List<Board> boardList;
    public BoardRepository(){
        this.boardList = new ArrayList<Board>();
    }

    public Optional<Board> findById(UUID boardId) {
        return this.boardList.stream().filter(s -> s.getId().equals(boardId))
                .findFirst();
    }

    public void save(Board board){
        this.boardList.add(board);
    }

}
