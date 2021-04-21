package ntut.csie.sslab.kanban.usecase.workspace.create.sticker;

import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.kanban.entity.model.workspace.Coordinate;

import java.util.UUID;

public class CreateStickerUseCaseImpl implements CreateStickerUseCase {
    private WorkspaceRepository workspaceRepository;

    public CreateStickerUseCaseImpl(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public void execute(CreateStickerInput input, CqrsCommandOutput output) {

    }

    @Override
    public CreateStickerInput newInput() {
        return new CreateStickerInputImpl();
    }

    private class CreateStickerInputImpl implements CreateStickerInput {
        private String workspaceId;
        private String content;
        private int size;
        private String color;
        private Coordinate position;

        @Override
        public String getWorkspaceId() {
            return workspaceId;
        }

        @Override
        public void setWorkspaceId(String workspaceId) {
            this.workspaceId = workspaceId;
        }

        @Override
        public String getContent() {
            return content;
        }

        @Override
        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int getSize() {
            return size;
        }

        @Override
        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public String getColor() {
            return color;
        }

        @Override
        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public Coordinate getPosition() {
            return position;
        }

        @Override
        public void setPosition(Coordinate position) {
            this.position = position;
        }
    }
}
