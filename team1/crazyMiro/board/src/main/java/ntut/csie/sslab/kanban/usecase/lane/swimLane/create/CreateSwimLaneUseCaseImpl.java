package ntut.csie.sslab.kanban.usecase.lane.swimLane.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.workflow.LaneType;
import ntut.csie.sslab.kanban.entity.model.workflow.Workflow;
import ntut.csie.sslab.kanban.usecase.ClientBoardContentMightExpire;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowRepository;

public class CreateSwimLaneUseCaseImpl implements CreateSwimLaneUseCase {
    private WorkflowRepository workflowRepository;
    private DomainEventBus domainEventBus;

    public CreateSwimLaneUseCaseImpl(WorkflowRepository workflowRepository,
                                     DomainEventBus domainEventBus) {

        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateSwimLaneInput input, CqrsCommandOutput output) {
        Workflow workflow= workflowRepository.findById(input.getWorkflowId()).orElse(null);
        if (null == workflow){
            output.setId(input.getWorkflowId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Create swimlane failed: workflow not found, workflow id = " + input.getWorkflowId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        String swimLaneId = workflow.createSwimLane(input.getParentId(),
                                                    input.getName(),
                                                    input.getWipLimit(),
                                                    LaneType.valueOf(input.getLaneType()),
                                                    input.getUserId(),
                                                    input.getUsername(),
                                                    input.getBoardId());
        workflowRepository.save(workflow);

        domainEventBus.postAll(workflow);

        output.setId(swimLaneId);
        output.setExitCode(ExitCode.SUCCESS);
    }

    @Override
    public CreateSwimLaneInput newInput() {
        return new CreateSwimLaneInputImpl();
    }

    private static class CreateSwimLaneInputImpl implements CreateSwimLaneInput{

        private String workflowId;
        private String parentId;
        private String name;
        private int wipLimit;
        private String laneType;
        private String userId;
        private String username;
        private String boardId;

        @Override
        public void setWorkflowId(String workflowId) {
            this.workflowId = workflowId;
        }

        @Override
        public String getWorkflowId() {
            return workflowId;
        }

        @Override
        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public String getParentId() {
            return parentId;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getWipLimit() {
            return wipLimit;
        }

        @Override
        public void setWipLimit(int wipLimit) {
            this.wipLimit = wipLimit;
        }

        @Override
        public String getLaneType() {
            return laneType;
        }

        @Override
        public void setLaneType(String laneType) {
            this.laneType = laneType;
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
