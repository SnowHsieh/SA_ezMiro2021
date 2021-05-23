package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

@Document(collection = "board")
public class BoardMdbData {

    @Id
    private String boardId;

    @Field("board_name")
    private String boardName;

    @Field("committed_figures")
    private Set<CommittedFigureMdbData> committedFigures;

    @Field("board_sessions")
    private Set<BoardSessionMdbData> boardSessions;

    public BoardMdbData(String boardId, String boardName) {
        this.boardId = boardId;
        this.boardName = boardName;
    }

    public BoardMdbData() {
        this.committedFigures = new HashSet<>();
        this.boardSessions = new HashSet<>();
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public List<CommittedFigureMdbData> getCommittedFigures() {
        List<CommittedFigureMdbData> committedFigureDatas = new ArrayList<>(committedFigures);
        committedFigureDatas.sort(Comparator.comparing(CommittedFigureMdbData::getzOrder));
        return committedFigureDatas;
    }

    public void setCommittedFigures(List<CommittedFigureMdbData> committedFigures) {
        this.committedFigures = new HashSet<>(committedFigures);
    }

    public List<BoardSessionMdbData> getBoardSessions() {
        return new ArrayList<>(boardSessions);
    }

    public void setBoardSessions(List<BoardSessionMdbData> boardSessions) {
        this.boardSessions = new HashSet<>(boardSessions);
    }
}
