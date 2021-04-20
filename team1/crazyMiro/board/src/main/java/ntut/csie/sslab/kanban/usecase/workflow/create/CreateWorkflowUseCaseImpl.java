package ntut.csie.sslab.kanban.usecase.workflow.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.entity.model.workflow.WorkflowBuilder;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowRepository;

public class CreateWorkflowUseCaseImpl implements CreateWorkflowUseCase {
    private WorkflowRepository workflowRepository;
    private DomainEventBus domainEventBus;

    public CreateWorkflowUseCaseImpl(WorkflowRepository workflowRepository, DomainEventBus domainEventBus) {
        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateWorkflowInput input, CqrsCommandOutput output) {
        Workflow workflow = WorkflowBuilder.newInstance()
                            .boardId(input.getBoardId())
                            .name(input.getWorkflowName())
                            .userId(input.getUserId())
                            .username(input.getUsername())
                            .build();

        workflowRepository.save(workflow);

        domainEventBus.postAll(workflow);

        output.setId(workflow.getWorkflowId());
        output.setExitCode(ExitCode.SUCCESS);
    }


    @Override
    public CreateWorkflowInput newInput() {
        return new CreateWorkflowInputImpl();
    }

    private static class CreateWorkflowInputImpl implements CreateWorkflowInput {
        private String boardId;
        private String name;
        private String userId;
        private String username;

        @Override
        public void setWorkflowName(String name) {
            this.name = name;
        }

        @Override
        public String getWorkflowName() {
            return name;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public String getUserId() {
            return userId;
        }

        @Override
        public void setUserId(String userId) {
            this.userId = userId;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public void setUsername(String username) {
            this.username = username;
        }
    }
}
