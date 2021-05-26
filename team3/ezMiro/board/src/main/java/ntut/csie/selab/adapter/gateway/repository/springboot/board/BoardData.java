package ntut.csie.selab.adapter.gateway.repository.springboot.board;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.CommittedWidgetData;
import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.entity.model.board.Cursor;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "board")
    private List<CommittedWidgetData> committedWidgets;

    public BoardData() {
    }

    public BoardData(String boardId) {
        this.boardId = boardId;
    }

    public BoardData(String boardId, String teamId, String boardName) {
        this.boardId = boardId;
        this.teamId = teamId;
        this.boardName = boardName;
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
}
