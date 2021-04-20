package ntut.csie.sslab.kanban.adapter.gateway.eventbus.google;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.kanban.entity.model.workflow.event.WorkflowCreated;
import ntut.csie.sslab.kanban.entity.model.workflow.event.WorkflowDeleted;
import ntut.csie.sslab.kanban.usecase.eventhandler.NotifyBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyBoardAdapter {

    private NotifyBoard notifyBoard;

    @Autowired
    public NotifyBoardAdapter(NotifyBoard notifyBoard) {
        this.notifyBoard = notifyBoard;
    }

    @Subscribe
    public void whenWorkflowCreated(WorkflowCreated workflowCreated) {
        notifyBoard.whenWorkflowCreated(workflowCreated);
    }

    @Subscribe
    public void whenWorkflowDeleted(WorkflowDeleted workflowDeleted) {
        notifyBoard.whenWorkflowDeleted(workflowDeleted);
    }

}
