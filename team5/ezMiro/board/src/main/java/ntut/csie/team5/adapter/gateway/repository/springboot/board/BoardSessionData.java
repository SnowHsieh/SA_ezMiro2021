package ntut.csie.team5.adapter.gateway.repository.springboot.board;

import javax.persistence.*;

@Entity
@Table(name = "board_session")
public class BoardSessionData {

    @Id
    @Column(name = "board_session_id")
    private  String boardSessionId;

    @Column(name = "board_id")
    private  String boardId;

    @Column(name = "user_id")
    private  String userId;

    public BoardSessionData() {
    }

    public BoardSessionData(String boardSessionId, String boardId, String userId) {
        this.boardSessionId = boardSessionId;
        this.boardId = boardId;
        this.userId = userId;
    }

    public String getBoardSessionId() {
        return boardSessionId;
    }

    public void setBoardSessionId(String boardSessionId) {
        this.boardSessionId = boardSessionId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
