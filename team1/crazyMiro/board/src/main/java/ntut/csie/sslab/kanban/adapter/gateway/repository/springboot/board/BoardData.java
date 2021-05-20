package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board;

import ntut.csie.sslab.kanban.entity.model.board.BoardSession;
import ntut.csie.sslab.kanban.entity.model.board.CommittedFigure;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="board")
public class BoardData {

    @Id
    @Column(name="board_id")
    private String boardId;

    @Column(name="board_name")
    private String boardName;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CommittedFigureData> committedFigures;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<BoardSessionData> boardSessions;

    public BoardData(String boardId, String boardName) {
        this.boardId = boardId;
        this.boardName = boardName;
    }

    public BoardData() {
        this.committedFigures = new ArrayList<>();
        this.boardSessions = new ArrayList<>();
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
        return committedFigures;
    }

    public void setCommittedFigures(List<CommittedFigureData> committedFigures) {
        this.committedFigures = committedFigures;
    }

    public List<BoardSessionData> getBoardSessions() {
        return boardSessions;
    }

    public void setBoardSessions(List<BoardSessionData> boardSessions) {
        this.boardSessions = boardSessions;
    }
}
