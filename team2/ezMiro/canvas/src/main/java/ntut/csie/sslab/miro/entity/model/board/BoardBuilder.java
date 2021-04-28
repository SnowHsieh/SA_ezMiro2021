package ntut.csie.sslab.miro.entity.model.board;

import java.util.UUID;

public class BoardBuilder {
    private String teamId;
    private String boardId;
    private String boardName;

    public static BoardBuilder newInstance() { return new BoardBuilder(); }

    public BoardBuilder teamId(String teamId) {
        this.teamId = teamId;
        return this;
    }

    public BoardBuilder boardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    public BoardBuilder boardName(String boardName) {
        this.boardName = boardName;
        return this;
    }

    public Board build() {
        boardId = UUID.randomUUID().toString();
        Board board = new Board(teamId, boardId, boardName);
        return board;
    }
}
