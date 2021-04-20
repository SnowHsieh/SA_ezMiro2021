package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2;

import ntut.csie.sslab.kanban.entity.model.board2.BoardMember;

import java.util.ArrayList;
import java.util.List;


public class BoardMemberMapper {

	public static BoardMemberData transformToData(BoardMember boardMember) {
		BoardMemberData data = new BoardMemberData(
				new BoardMemberDataId(boardMember.getBoardId(), boardMember.getUserId()),
				boardMember.getMemberType().name());
		return data;
	}


	public static List<BoardMemberData> transformToData(List<BoardMember> boardMembers) {
		List<BoardMemberData> datas = new ArrayList<>();

		boardMembers.stream().forEach(x -> {
			datas.add(transformToData(x));
		});

		return datas;
	}
}