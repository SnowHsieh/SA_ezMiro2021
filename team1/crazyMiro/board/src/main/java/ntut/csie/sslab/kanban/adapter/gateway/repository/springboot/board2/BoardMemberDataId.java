package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BoardMemberDataId implements Serializable {
    @Column(name = "board_id", nullable = false)
    private String boardId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    public BoardMemberDataId() {
    }

    public BoardMemberDataId(String boardId, String userId) {
        this.boardId = boardId;
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardMemberDataId)) return false;
        BoardMemberDataId that = (BoardMemberDataId) o;
        return Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getBoardId(), that.getBoardId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getBoardId());
    }
}
