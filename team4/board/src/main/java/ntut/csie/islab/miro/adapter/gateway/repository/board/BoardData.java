package ntut.csie.islab.miro.adapter.gateway.repository.board;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "board")
public class BoardData {

    @Id
    @Column(name = "board_id")
    private String boardId; // TODO: change to UUID?

    @Column(name = "team_id")
    private String teamId; // TODO: change to UUID?

    @Column(name = "board_name")
    private String boardName;


//    @OrderBy("zOrder")
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CommittedFigureData> committedFigures;
//
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<BoardSessionData> boardSessions;


    public BoardData(String teamId, String boardId, String boardName) {
        this.boardId = boardId;
        this.teamId = teamId;
        this.boardName = boardName;
    }

    public BoardData() {
//        this.committedFigures = new HashSet<>();
        this.boardSessions = new HashSet<>();
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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