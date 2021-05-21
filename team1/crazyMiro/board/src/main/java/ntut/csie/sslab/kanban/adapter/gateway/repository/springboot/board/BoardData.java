package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board;

import ntut.csie.sslab.kanban.entity.model.board.BoardSession;
import ntut.csie.sslab.kanban.entity.model.board.CommittedFigure;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="board")
public class BoardData {

    @Id
    @Column(name="board_id")
    private String boardId;

    @Column(name="board_name")
    private String boardName;

    @OrderBy("zOrder")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CommittedFigureData> committedFigures;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<BoardSessionData> boardSessions;

    public BoardData(String boardId, String boardName) {
        this.boardId = boardId;
        this.boardName = boardName;
    }

    public BoardData() {
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

    public List<CommittedFigureData> getCommittedFigures() {
        List<CommittedFigureData> committedFigureDatas = new ArrayList<>(committedFigures);
        committedFigureDatas.sort(Comparator.comparing(CommittedFigureData::getzOrder));
        return committedFigureDatas;
    }

    public void setCommittedFigures(List<CommittedFigureData> committedFigures) {
        this.committedFigures = new HashSet<>(committedFigures);
    }

    public List<BoardSessionData> getBoardSessions() {
        return new ArrayList<>(boardSessions);
    }

    public void setBoardSessions(List<BoardSessionData> boardSessions) {
        this.boardSessions = new HashSet<>(boardSessions);
    }
}
