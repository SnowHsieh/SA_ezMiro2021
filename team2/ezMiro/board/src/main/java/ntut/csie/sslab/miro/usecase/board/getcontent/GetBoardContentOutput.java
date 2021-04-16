package ntut.csie.sslab.miro.usecase.board.getcontent;

import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.miro.usecase.board.BoardMemberDto;
import ntut.csie.sslab.miro.usecase.board.CommittedWorkflowDto;
import ntut.csie.sslab.miro.usecase.card.CardDto;
import ntut.csie.sslab.miro.usecase.workflow.WorkflowDto;

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
