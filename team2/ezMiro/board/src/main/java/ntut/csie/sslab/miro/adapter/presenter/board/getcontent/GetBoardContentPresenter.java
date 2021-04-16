package ntut.csie.sslab.miro.adapter.presenter.board.getcontent;

import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Result;
import ntut.csie.sslab.miro.usecase.board.BoardMemberDto;
import ntut.csie.sslab.miro.usecase.board.CommittedWorkflowDto;
import ntut.csie.sslab.miro.usecase.board.getcontent.GetBoardContentOutput;
import ntut.csie.sslab.miro.usecase.card.CardDto;
import ntut.csie.sslab.miro.usecase.lane.LaneDto;
import ntut.csie.sslab.miro.usecase.workflow.WorkflowDto;

import java.util.ArrayList;
import java.util.List;

public class GetBoardContentPresenter extends Result implements Presenter<BoardContentViewModel>, GetBoardContentOutput {

    private String boardId;
    private List<BoardMemberDto> boardMemberDtos;
    private List<WorkflowDto> workflowDtos;
    private List<CommittedWorkflowDto> committedWorkflowDtos;
    private List<CardDto> cardDtos;

    public GetBoardContentPresenter() {
        boardMemberDtos = new ArrayList<>();
        workflowDtos = new ArrayList<>();
        committedWorkflowDtos = new ArrayList<>();
        cardDtos = new ArrayList<>();
    }

    @Override
    public BoardContentViewModel buildViewModel() {
        BoardContentViewModel boardContentViewModel = new BoardContentViewModel();
        List<WorkflowDto> orderedWorkflowDtos = new ArrayList<>();
        for(CommittedWorkflowDto committedWorkflowDto: committedWorkflowDtos) {
            for (WorkflowDto workflowDto : workflowDtos) {
                if(committedWorkflowDto.getWorkflowId().equals(workflowDto.getWorkflowId()))
                    orderedWorkflowDtos.add(workflowDto);
            }
        }

        for (WorkflowDto workflowDto : orderedWorkflowDtos) {
            workflowDto.setLanes(bindCardDtoToLaneDto(workflowDto.getLanes()));
        }

        boardContentViewModel.setBoardId(boardId);
        boardContentViewModel.setWorkflows(orderedWorkflowDtos);
        boardContentViewModel.setBoardMembers(boardMemberDtos);
        return boardContentViewModel;
    }

    private List<LaneDto> bindCardDtoToLaneDto(List<LaneDto> laneDtos) {
        for(LaneDto laneDto : laneDtos) {
            for(CardDto cardDto : cardDtos) {
                if(laneDto.getLaneId().equals(cardDto.getLaneId())) {
                    laneDto.addCardDto(cardDto);
                }
            }
            laneDto.setLanes(bindCardDtoToLaneDto(laneDto.getLanes()));
        }

        return laneDtos;
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public GetBoardContentOutput setBoardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    @Override
    public List<BoardMemberDto> getBoardMembers() {
        return boardMemberDtos;
    }

    @Override
    public GetBoardContentOutput setBoardMembers(List<BoardMemberDto> boardMemberDtos) {
        this.boardMemberDtos = boardMemberDtos;
        return this;
    }

    @Override
    public List<WorkflowDto> getWorkflows() {
        return workflowDtos;
    }

    @Override
    public GetBoardContentOutput setWorkflows(List<WorkflowDto> workflowDtos) {
        this.workflowDtos = workflowDtos;
        return this;
    }

    @Override
    public List<CommittedWorkflowDto> getCommittedWorkflows() {
        return committedWorkflowDtos;
    }

    @Override
    public GetBoardContentOutput setCommittedWorkflows(List<CommittedWorkflowDto> committedWorkflowDtos) {
        this.committedWorkflowDtos = committedWorkflowDtos;
        return this;
    }

    @Override
    public List<CardDto> getCards() {
        return cardDtos;
    }

    @Override
    public GetBoardContentOutput setCards(List<CardDto> cardDtos) {
        this.cardDtos = cardDtos;
        return this;
    }

}
