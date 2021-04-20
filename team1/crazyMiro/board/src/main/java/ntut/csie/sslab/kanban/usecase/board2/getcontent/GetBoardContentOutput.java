package ntut.csie.sslab.kanban.usecase.board2.getcontent;

import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.kanban.usecase.board2.BoardMemberDto;
import ntut.csie.sslab.kanban.usecase.board2.CommittedWorkflowDto;
import ntut.csie.sslab.kanban.usecase.card.CardDto;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowDto;

import java.util.List;

public interface GetBoardContentOutput extends Output {

    GetBoardContentOutput setBoardId(String boardId);

    String getBoardId();

    GetBoardContentOutput setBoardMembers(List<BoardMemberDto> boardMemberDtos);

    List<BoardMemberDto> getBoardMembers();

    GetBoardContentOutput setWorkflows(List<WorkflowDto> workflowDtos);

    List<WorkflowDto> getWorkflows();

    GetBoardContentOutput setCards(List<CardDto> cardDtosInBoard);

    List<CardDto> getCards();

    GetBoardContentOutput setCommittedWorkflows(List<CommittedWorkflowDto> committedWorkflowDtos);

    List<CommittedWorkflowDto> getCommittedWorkflows();
}
