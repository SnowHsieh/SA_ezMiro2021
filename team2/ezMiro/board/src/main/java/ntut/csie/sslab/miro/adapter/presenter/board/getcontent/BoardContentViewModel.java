package ntut.csie.sslab.miro.adapter.presenter.board.getcontent;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.miro.usecase.board.BoardMemberDto;
import ntut.csie.sslab.miro.usecase.workflow.WorkflowDto;

import java.util.List;

public class BoardContentViewModel implements ViewModel {
    private String boardId;
    private List<WorkflowDto> workflows;
    private List<BoardMemberDto> boardMembers;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<WorkflowDto> getWorkflows() {
        return workflows;
    }

    public void setWorkflows(List<WorkflowDto> workflows) {
        this.workflows = workflows;
    }

    public List<BoardMemberDto> getBoardMembers() {
        return boardMembers;
    }

    public void setBoardMembers(List<BoardMemberDto> boardMembers) {
        this.boardMembers = boardMembers;
    }
}
