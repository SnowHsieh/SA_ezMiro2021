package ntut.csie.sslab.kanban.usecase.sticker.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workspace.Coordinate;
import ntut.csie.sslab.kanban.entity.model.workspace.Workspace;
import ntut.csie.sslab.kanban.usecase.workspace.WorkspaceRepository;

public class CreateStickerUseCaseImpl implements CreateStickerUseCase {
    private WorkspaceRepository workspaceRepository;
    private DomainEventBus domainEventBus;

    public CreateStickerUseCaseImpl(WorkspaceRepository workspaceRepository, DomainEventBus domainEventBus) {
        this.workspaceRepository = workspaceRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateStickerInput input, CqrsCommandOutput output) {
        try {
            Workspace workspace = workspaceRepository.findById(input.getWorkspaceId()).get();
            String stickerId = workspace.createSticker(
                    input.getContent(),
                    input.getSize(),
                    input.getColor(),
                    input.getPosition());
            workspaceRepository.save(workspace);
            domainEventBus.postAll(workspace);

            output.setId(stickerId).setExitCode(ExitCode.SUCCESS);
        }catch (Exception e) {
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }
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
