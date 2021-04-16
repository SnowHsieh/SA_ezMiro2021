package ntut.csie.sslab.miro.usecase.board;


import ntut.csie.sslab.miro.entity.model.board.BoardMember;

import java.util.ArrayList;
import java.util.List;

public class ConvertBoardMemberToDto {
    public static BoardMemberDto transform(BoardMember boardMember) {
        BoardMemberDto dto = new BoardMemberDto();
        dto.setBoardId(boardMember.getBoardId());
        dto.setUserId(boardMember.getUserId());
        dto.setMemberType(boardMember.getMemberType());
        return dto;
    }

    public static List<BoardMemberDto> transform(List<BoardMember> boardMembers) {
        List<BoardMemberDto> boardMemberDtos = new ArrayList<>();
        for(BoardMember boardMember : boardMembers) {
            boardMemberDtos.add(transform(boardMember));
        }

        return boardMemberDtos;
    }
}
