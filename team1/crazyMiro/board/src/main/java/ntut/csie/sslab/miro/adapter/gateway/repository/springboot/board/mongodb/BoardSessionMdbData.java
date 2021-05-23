package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Document(collection = "board_session")
public class BoardSessionMdbData {

    @Field("board_session_id")
    private String boardSessionId;

    @Field("user_id")
    private String userId;

    @Field("board_id")
    private String boardId;

    public BoardSessionMdbData() {
    }

    public BoardSessionMdbData(String userId, String boardId, String boardSessionId) {
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
