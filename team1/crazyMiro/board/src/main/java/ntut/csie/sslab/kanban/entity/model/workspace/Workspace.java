package ntut.csie.sslab.kanban.entity.model.workspace;

import ntut.csie.sslab.ddd.model.AggregateRoot;
import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.kanban.entity.model.workspace.event.StickerCreated;
import ntut.csie.sslab.kanban.entity.model.workspace.event.WorkspaceCreated;

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
        addDomainEvent(new WorkspaceCreated(workspaceId, boardId));
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
        addDomainEvent(new StickerCreated(workspaceId, sticker.getFigureId(), content, size, color, position));
        return sticker.getFigureId();
    }
}
