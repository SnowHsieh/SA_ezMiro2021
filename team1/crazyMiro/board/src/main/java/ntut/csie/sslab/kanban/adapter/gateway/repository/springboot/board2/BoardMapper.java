package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2;

import ntut.csie.sslab.kanban.entity.model.board2.Board2;
import ntut.csie.sslab.kanban.entity.model.board2.BoardMemberType;

import java.util.ArrayList;
import java.util.List;

public class BoardMapper {

	public static List<Board2> transformToDomain(List<BoardData> datas) {
		List<Board2> board2s = new ArrayList<>();
		datas.forEach(x -> board2s.add(transformToDomain(x)));
		return board2s;
	}


	public static Board2 transformToDomain(BoardData data) {

		Board2 board2 = new Board2(data.getTeamId(), data.getBoardId(), data.getName(), "");
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
			board2.becameBoardMember(boardMemberType, boardMemberData.getUserId());
		}

		if(data.getCommittedWorkflowDatas() != null)
			for (CommittedWorkflowData committedWorkflowData : data.getCommittedWorkflowDatas()) {
				board2.commitWorkflow(committedWorkflowData.getWorkflowId());
			}

		board2.clearDomainEvents();
		return board2;
	}

	public static BoardData transformToData(Board2 board2) {
		BoardData data = new BoardData(
				board2.getTeamId(),
				board2.getBoardId(),
				board2.getName());

		data.setCommittedWorkflowDatas(CommittedWorkflowMapper.
				transformToData(board2.getCommittedWorkflows()));

		data.setBoardMemberDatas(BoardMemberMapper.transformToData(board2.getBoardMembers()));
		
		return data;
	}
}
