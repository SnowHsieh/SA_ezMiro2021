package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;

import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardMemberType;

import java.util.ArrayList;
import java.util.List;

public class BoardMapper {

	public static List<Board> transformToDomain(List<BoardData> datas) {
		List<Board> boards = new ArrayList<>();
		datas.forEach(x -> boards.add(transformToDomain(x)));
		return boards;
	}


	public static Board transformToDomain(BoardData data) {

		Board board = new Board("", data.getTeamId(), data.getBoardId(), data.getName());
		for(BoardMemberData boardMemberData : data.getBoardMemberDatas()) {
			BoardMemberType boardMemberType = BoardMemberType.Member;
			switch (boardMemberData.getMemberType()) {
				case("Manager"):
					boardMemberType = BoardMemberType.Manager;
					break;
				case("Member"):
					boardMemberType = BoardMemberType.Member;
					break;
			}
			board.addBoardMember(boardMemberType, boardMemberData.getUserId());
		}


		if(data.getCommittedWorkflowDatas() != null)
			for (CommittedWorkflowData committedWorkflowData : data.getCommittedWorkflowDatas()) {
				board.addWorkflow(committedWorkflowData.getWorkflowId());
			}

		board.clearDomainEvents();
		return board;
	}

	public static BoardData transformToData(Board board) {
		BoardData data = new BoardData(
				board.getTeamId(),
				board.getBoardId(),
				board.getName());

		data.setCommittedWorkflowDatas(CommittedWorkflowMapper.
				transformToData(board.getCommittedWorkflows()));

		data.setBoardMemberDatas(BoardMemberMapper.transformToData(board.getBoardMembers()));
		
		return data;
	}
}
