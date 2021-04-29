package ntut.csie.islab.miro.adapter.repository.board;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.figure.entity.model.figure.Figure;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.FileWriter;
import java.util.*;


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
