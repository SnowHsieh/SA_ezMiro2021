package ntut.csie.islab.miro.adapter.repository.board;

import ntut.csie.islab.miro.entity.board.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardRepository {
    private List<Board> boardList;
    public BoardRepository(){
        this.boardList = new ArrayList<Board>();
    }
    public void save(Board board){
        this.boardList.add(board);
    }

}
