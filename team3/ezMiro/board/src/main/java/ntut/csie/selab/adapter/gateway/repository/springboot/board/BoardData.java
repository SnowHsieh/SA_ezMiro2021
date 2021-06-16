package ntut.csie.selab.adapter.gateway.repository.springboot.board;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="board")
public class BoardData {

    @Id
    @Column(name="board_id")
    private String boardId;

    @Column(name="team_id")
    private String teamId;

    @Column(name="board_name")
    private String boardName;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<CommittedWidgetData> committedWidgets;

    public BoardData() {
    }

    public BoardData(String boardId) {
        this.boardId = boardId;
    }

    public BoardData(String boardId, String teamId, String boardName, List<CommittedWidgetData> committedWidgets) {
        this.boardId = boardId;
        this.teamId = teamId;
        this.boardName = boardName;
        this.committedWidgets = new HashSet<>();
        this.committedWidgets.addAll(committedWidgets);
    }

    public String getBoardId() {
        return boardId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getBoardName() {
        return boardName;
    }

    public Set<CommittedWidgetData> getCommittedWidgets() {
        return committedWidgets;
    }
}
