package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="board_session")
public class BoardSessionData {

    @Id
    @Column(name="board_session_id")
    private String boardSessionId;

    @Column(name="user_id")
    private String userId;

    @Column(name="board_id")
    private String boardId;

    public BoardSessionData() {
    }

    public BoardSessionData(String userId, String boardId, String boardSessionId) {
        this.userId = userId;
        this.boardId = boardId;
        this.boardSessionId = boardSessionId;
    }

    public String getBoardSessionId() {
        return boardSessionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBoardId() {
        return boardId;
    }
}
