package ntut.csie.sslab.kanban.adapter.gateway.eventbus.google;

import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.kanban.entity.model.card.event.CardCreated;
import ntut.csie.sslab.kanban.entity.model.card.event.CardDeleted;
import ntut.csie.sslab.kanban.entity.model.workflow.event.CardCommitted;
import ntut.csie.sslab.kanban.entity.model.workflow.event.CardMoved;
import ntut.csie.sslab.kanban.usecase.eventhandler.NotifyWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotifyWorkflowAdapter {

    private NotifyWorkflow notifyWorkflow;

    @Autowired
    public NotifyWorkflowAdapter(NotifyWorkflow notifyWorkflow) {
        this.notifyWorkflow = notifyWorkflow;
    }

    @Subscribe
    public void whenCardCreated(CardCreated event) {
        notifyWorkflow.whenCardCreated(event);
    }

    @Subscribe
    public void whenCardDeleted(CardDeleted event) {
        notifyWorkflow.whenCardDeleted(event);
    }

    @Subscribe
    public void whenCardMoved(CardMoved event) {
        notifyWorkflow.whenCardMoved(event);
    }

}
