package ntut.csie.sslab.miro.usecase.board.getcontent;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.usecase.ClientBoardContentMightExpire;
import ntut.csie.sslab.miro.usecase.board.*;
import ntut.csie.sslab.miro.usecase.card.CardDto;
import ntut.csie.sslab.miro.usecase.card.CardRepository;
import ntut.csie.sslab.miro.usecase.card.ConvertCardToDto;
import ntut.csie.sslab.miro.usecase.workflow.ConvertWorkflowsToDto;
import ntut.csie.sslab.miro.usecase.workflow.WorkflowDto;
import ntut.csie.sslab.miro.usecase.workflow.WorkflowRepository;

import java.util.List;

public class GetBoardContentUseCaseImpl implements GetBoardContentUseCase, GetBoardContentInput{

    private String boardId;
    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private CardRepository cardRepository;
    private DomainEventBus domainEventBus;

    public GetBoardContentUseCaseImpl(BoardRepository boardRepository,
                                      WorkflowRepository workflowRepository,
                                      CardRepository cardRepository,
                                      DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(GetBoardContentInput input, GetBoardContentOutput output) {

        Board board = boardRepository.findById(input.getBoardId()).orElse(null);
        if (null == board){
            output.setBoardId(input.getBoardId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Get board failed: board not found, board id = " + input.getBoardId());
            domainEventBus.post(new ClientBoardContentMightExpire(input.getBoardId()));
            return;
        }

        List<BoardMemberDto> boardMemberDtos = ConvertBoardMemberToDto.transform(board.getBoardMembers());
        List<WorkflowDto> workflowDtos = ConvertWorkflowsToDto.transform(workflowRepository.getWorkflowsByBoardId(input.getBoardId()));




        List<CardDto> cardDtosInBoard = ConvertCardToDto.transform(cardRepository.getCardsByBoardId(input.getBoardId()));
        List<CommittedWorkflowDto> committedWorkflowDtos = ConvertCommittedWorkflowToDto.transform(board.getCommittedWorkflows());

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
