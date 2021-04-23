package ntut.csie.team5.entity.model.board;

import java.util.UUID;

public class BoardBuilder {

    private String boardId;
    private String name;
    private String projectId;

    public static BoardBuilder newInstance() {
        return new BoardBuilder();
    }

    public BoardBuilder projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public BoardBuilder name(String name) {
        this.name = name;
        return this;
    }

    public Board build() {
        boardId = UUID.randomUUID().toString();
        Board board = new Board(boardId, name, projectId);
        return board;
    }
}
