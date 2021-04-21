package ntut.csie.sslab.kanban.entity.model.workspace;

import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Workspace extends AggregateRoot<String> {

    private String boardId;
    private String workspaceId;
    private List<Figure> figures;

    public Workspace(String workspaceId, String boardId) {
        super(workspaceId);
        this.boardId = boardId;
        this.workspaceId = workspaceId;
        this.figures = new ArrayList<>();
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public List<Figure> getAllFigures() {
        return figures;
    }

    public Optional<Figure> getFigureById(String id) {
        return figures.stream().filter(x -> x.getFigureId().equals(id)).findFirst();
    }

    public String createSticker(String content, int size, String color, Coordinate position) {
        Sticker sticker = new Sticker(UUID.randomUUID().toString(), content, size, color, position);
        figures.add(sticker);
        return sticker.getFigureId();
    }
}
