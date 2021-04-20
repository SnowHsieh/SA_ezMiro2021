package ntut.csie.sslab.kanban.usecase.lane.rename;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.ClientBoardContentMightExpire;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowRepository;

public class RenameLaneUseCaseImpl implements RenameLaneUseCase {

    private WorkflowRepository workflowRepository;
    private DomainEventBus domainEventBus;

    public RenameLaneUseCaseImpl(WorkflowRepository workflowRepository, DomainEventBus domainEventBus) {
        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(RenameLaneInput input, CqrsCommandOutput output) {
        Workflow workflow= workflowRepository.findById(input.getWorkflowId()).orElse(null);
        if (null == workflow){
            output.setId(input.getWorkflowId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Rename lane failed: workflow not found, workflow id = " + input.getWorkflowId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }
        workflow.renameLane(input.getLaneId(), input.getNewName(), input.getUserId(), input.getUsername());

        workflowRepository.save(workflow);
        domainEventBus.postAll(workflow);

        output.setId(input.getLaneId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    @Override
    public RenameLaneInput newInput() {
        return new RenameLaneInputImpl();
    }

    private static class RenameLaneInputImpl implements RenameLaneInput {
        private String boardId;
        private String workflowId;
        private String laneId;
        private String newName;
        private String userId;
        private String username;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getWorkflowId() {
            return workflowId;
        }

        @Override
        public void setWorkflowId(String workflowId) {
            this.workflowId = workflowId;
        }

        @Override
        public String getLaneId() {
            return laneId;
        }

        @Override
        public void setLaneId(String laneId) {
            this.laneId = laneId;
        }

        @Override
        public String getNewName() {
            return newName;
        }

        @Override
        public void setNewName(String newName) {
            this.newName = newName;
        }

        @Override
        public String getUserId() { return userId; }

        @Override
        public void setUserId(String userId) { this.userId = userId; }

        @Override
        public String getUsername() { return username; }

        @Override
        public void setUsername(String username) { this.username = username; }
    }
}
