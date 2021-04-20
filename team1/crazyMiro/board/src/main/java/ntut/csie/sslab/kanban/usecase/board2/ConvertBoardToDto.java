package ntut.csie.sslab.kanban.usecase.board2;

import ntut.csie.sslab.kanban.entity.model.board2.Board2;
import ntut.csie.sslab.kanban.entity.model.board2.BoardMember;
import ntut.csie.sslab.kanban.entity.model.board2.CommittedWorkflow;

import java.util.ArrayList;
import java.util.List;

public class ConvertBoardToDto {
	public static BoardDto transform(Board2 board2) {
		BoardDto dto = new BoardDto();
		dto.setBoardId(board2.getBoardId());
		dto.setProjectId(board2.getTeamId());
		dto.setTitle(board2.getName());
		List<BoardMemberDto> boardMemberDtos = new ArrayList<>();
		for(BoardMember boardMember: board2.getBoardMembers()){
			boardMemberDtos.add(ConvertBoardMemberToDto.transform(boardMember));
		}

		List<CommittedWorkflowDto> committedWorkflowDtos = new ArrayList<>();
		for(CommittedWorkflow committedWorkflow : board2.getCommittedWorkflows()) {
			committedWorkflowDtos.add(ConvertCommittedWorkflowToDto.transform(committedWorkflow));
		}
		dto.setCommittedWorkflows(committedWorkflowDtos);

		dto.setBoardMembers(boardMemberDtos);
		return dto;
	}

	public static List<BoardDto> transform(List<Board2> board2s) {
		List<BoardDto> allBoardDtos = new ArrayList<>();
		for(Board2 board2 : board2s) {
			allBoardDtos.add(ConvertBoardToDto.transform(board2));
		}
		return allBoardDtos;
	}
}
