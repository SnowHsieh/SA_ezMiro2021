package ntut.csie.sslab.kanban.usecase.workspace.create.sticker;

import ntut.csie.sslab.ddd.usecase.Input;
import ntut.csie.sslab.kanban.entity.model.workspace.Coordinate;

public interface CreateStickerInput extends Input {
    void setWorkspaceId(String workspaceId);

    String getWorkspaceId();

    void setContent(String content);

    String getContent();

    void setSize(int size);

    int getSize();

    void setColor(String color);

    String getColor();

    void setPosition(Coordinate position);

    Coordinate getPosition();




}
