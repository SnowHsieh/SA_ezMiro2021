package ntut.csie.sslab.miro.usecase.board;

import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardMember;
import ntut.csie.sslab.miro.entity.model.board.CommittedWorkflow;

import java.util.ArrayList;
import java.util.List;

public class ConvertBoardToDto {
	public static BoardDto transform(Board board) {
		BoardDto dto = new BoardDto();
		dto.setBoardId(board.getBoardId());
		dto.setProjectId(board.getTeamId());
		dto.setTitle(board.getName());
		List<BoardMemberDto> boardMemberDtos = new ArrayList<>();
		for(BoardMember boardMember: board.getBoardMembers()){
			boardMemberDtos.add(ConvertBoardMemberToDto.transform(boardMember));
		}

		List<CommittedWorkflowDto> committedWorkflowDtos = new ArrayList<>();
		for(CommittedWorkflow committedWorkflow : board.getCommittedWorkflows()) {
			committedWorkflowDtos.add(ConvertCommittedWorkflowToDto.transform(committedWorkflow));
		}
		dto.setCommittedWorkflows(committedWorkflowDtos);

		dto.setBoardMembers(boardMemberDtos);
		return dto;
	}

	public static List<BoardDto> transform(List<Board> boards) {
		List<BoardDto> allBoardDtos = new ArrayList<>();
		for(Board board: boards) {
			allBoardDtos.add(ConvertBoardToDto.transform(board));
		}
		return allBoardDtos;
	}
}
