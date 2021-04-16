package ntut.csie.sslab.miro.entity.model.board.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class WorkflowUncommitted extends DomainEvent {

    private final String boardId;
    private final String workflowId;

    public WorkflowUncommitted(String boardId, String workflowId) {
        super(DateProvider.now());
        this.boardId= boardId;
        this.workflowId= workflowId;
    }

    public String boardId(){
        return boardId;
    }

    public String workflowId(){
        return workflowId;
    }
}
