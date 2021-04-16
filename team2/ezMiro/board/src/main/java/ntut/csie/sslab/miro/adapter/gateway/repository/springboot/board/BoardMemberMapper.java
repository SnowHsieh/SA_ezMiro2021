package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;

import ntut.csie.sslab.miro.entity.model.board.BoardMember;

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