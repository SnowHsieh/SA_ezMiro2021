package ntut.csie.sslab.kanban.usecase.workspace.create;

import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workspace.Workspace;
import ntut.csie.sslab.kanban.usecase.workspace.WorkspaceRepository;
import ntut.csie.sslab.kanban.usecase.workspace.create.CreateWorkspaceInput;
import ntut.csie.sslab.kanban.usecase.workspace.create.CreateWorkspaceUseCase;

import java.util.UUID;

public class CreateWorkspaceUseCaseImpl implements CreateWorkspaceUseCase {
    private WorkspaceRepository workspaceRepository;

    public CreateWorkspaceUseCaseImpl(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public void execute(CreateWorkspaceInput input, CqrsCommandOutput output) {
        try{
            Workspace workspace = new Workspace(UUID.randomUUID().toString(), input.getBoardId());
            workspaceRepository.save(workspace);
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
