package ntut.csie.sslab.kanban.usecase.workspace.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workspace.Workspace;
import ntut.csie.sslab.kanban.usecase.workspace.WorkspaceRepository;

import java.util.UUID;

public class CreateWorkspaceUseCaseImpl implements CreateWorkspaceUseCase {
    private WorkspaceRepository workspaceRepository;
    private DomainEventBus domainEventBus;

    public CreateWorkspaceUseCaseImpl(WorkspaceRepository workspaceRepository, DomainEventBus domainEventBus) {
        this.workspaceRepository = workspaceRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateWorkspaceInput input, CqrsCommandOutput output) {
        try{
            Workspace workspace = new Workspace(UUID.randomUUID().toString(), input.getBoardId());
            workspaceRepository.save(workspace);
            domainEventBus.postAll(workspace);
            output.setId(workspace.getId())
                    .setExitCode(ExitCode.SUCCESS);
        }catch (Exception e) {
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }
    }

    @Override
    public CreateWorkspaceInput newInput() {
        return new CreateWorkspaceInputImpl();
    }

    private class CreateWorkspaceInputImpl implements CreateWorkspaceInput {
        private String boardId;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }
    }
}
