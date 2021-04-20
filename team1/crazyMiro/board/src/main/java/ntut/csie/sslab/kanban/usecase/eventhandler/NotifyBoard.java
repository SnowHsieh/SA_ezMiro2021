package ntut.csie.sslab.kanban.usecase.eventhandler;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.kanban.entity.model.board2.Board2;
import ntut.csie.sslab.kanban.usecase.board2.Board2Repository;
import ntut.csie.sslab.kanban.entity.model.workflow.event.WorkflowCreated;
import ntut.csie.sslab.kanban.entity.model.workflow.event.WorkflowDeleted;

import java.util.Optional;

public class NotifyBoard {
    private Board2Repository board2Repository;
    private DomainEventBus domainEventBus;

    public NotifyBoard(Board2Repository board2Repository,
                       DomainEventBus domainEventBus) {
        this.board2Repository = board2Repository;
        this.domainEventBus = domainEventBus;
    }

    public void whenWorkflowCreated(WorkflowCreated workflowCreated) {
        Optional<Board2> board = board2Repository.findById(workflowCreated.boardId());
        if (!board.isPresent())
            throw new RuntimeException("Board not found, board id = " + workflowCreated.boardId());

        board.get().commitWorkflow(workflowCreated.workflowId());
        board2Repository.save(board.get());

        domainEventBus.postAll(board.get());
    }

    public void whenWorkflowDeleted(WorkflowDeleted workflowDeleted) {
        Optional<Board2> board = board2Repository.findById(workflowDeleted.boardId());
        if (!board.isPresent())
            throw new RuntimeException("Board not found, board id = " + workflowDeleted.boardId());

        board.get().uncommitworkflow(workflowDeleted.workflowId());
        board2Repository.save(board.get());

        domainEventBus.postAll(board.get());
    }


    

}
