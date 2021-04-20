package ntut.csie.sslab.kanban.usecase.board2.getcontent;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.board2.Board2;
import ntut.csie.sslab.kanban.usecase.ClientBoardContentMightExpire;
import ntut.csie.sslab.kanban.usecase.board2.*;
import ntut.csie.sslab.kanban.usecase.card.CardDto;
import ntut.csie.sslab.kanban.usecase.card.CardRepository;
import ntut.csie.sslab.kanban.usecase.card.ConvertCardToDto;
import ntut.csie.sslab.kanban.usecase.workflow.ConvertWorkflowsToDto;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowDto;
import ntut.csie.sslab.kanban.usecase.workflow.WorkflowRepository;

import java.util.List;

public class GetBoardContentUseCaseImpl implements GetBoardContentUseCase, GetBoardContentInput{

    private String boardId;
    private Board2Repository board2Repository;
    private WorkflowRepository workflowRepository;
    private CardRepository cardRepository;
    private DomainEventBus domainEventBus;

    public GetBoardContentUseCaseImpl(Board2Repository board2Repository,
                                      WorkflowRepository workflowRepository,
                                      CardRepository cardRepository,
                                      DomainEventBus domainEventBus) {
        this.board2Repository = board2Repository;
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(GetBoardContentInput input, GetBoardContentOutput output) {

        Board2 board2 = board2Repository.findById(input.getBoardId()).orElse(null);
        if (null == board2){
            output.setBoardId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Get board failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        List<BoardMemberDto> boardMemberDtos = ConvertBoardMemberToDto.transform(board2.getBoardMembers());
        List<WorkflowDto> workflowDtos = ConvertWorkflowsToDto.transform(workflowRepository.getWorkflowsByBoardId(input.getBoardId()));




        List<CardDto> cardDtosInBoard = ConvertCardToDto.transform(cardRepository.getCardsByBoardId(input.getBoardId()));
        List<CommittedWorkflowDto> committedWorkflowDtos = ConvertCommittedWorkflowToDto.transform(board2.getCommittedWorkflows());

        output.setBoardId(input.getBoardId())
                .setBoardMembers(boardMemberDtos)
                .setWorkflows(workflowDtos)
                .setCommittedWorkflows(committedWorkflowDtos)
                .setCards(cardDtosInBoard);
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
