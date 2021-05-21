package ntut.csie.islab.miro.adapter.gateway.repository.board;


import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.BoardSession;
import ntut.csie.islab.miro.entity.model.board.BoardSessionId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoardMapper {

    public static Board transformToDomain(BoardData boardData) {
        Board board = new Board(UUID.fromString(boardData.getTeamId()), UUID.fromString(boardData.getBoardId()), boardData.getBoardName());
//        for (CommittedFigureData committedFigureData : boardData.getCommittedFigures()) {
//            board.commitFigure(committedFigureData.getFigureId());
//        }
        List<BoardSession> boardSessionList = new ArrayList<>();
        for (BoardSessionData boardSessionData : boardData.getBoardSessions()) {
            boardSessionList.add(new BoardSession(UUID.fromString(boardSessionData.getUserId()),
                            UUID.fromString(boardSessionData.getBoardId()),
                            new BoardSessionId(boardSessionData.getBoardSessionId())
                    )
            );
        }
        board.setBoardSessionList(boardSessionList);
        board.clearDomainEvents();
        return board;
    }

    public static List<Board> transformToDomain(List<BoardData> boardDatas) {
        List<Board> boards = new ArrayList<>();
        for (BoardData boardData : boardDatas) {
            Board board = transformToDomain(boardData);
            boards.add(board);
        }
        return boards;
    }

    public static BoardData transformToData(Board board) {
        BoardData boardData = new BoardData(board.getTeamId().toString(),
                board.getBoardId().toString(),
                board.getBoardName()
        );
        List<BoardSessionData> boardSessionDatas = new ArrayList<>();
        for (BoardSession boardSession : board.getBoardSessionList()) {
            boardSessionDatas.add(new BoardSessionData(boardSession.getUserId().toString(),
                    boardSession.getBoardId().toString(),
                    boardSession.getBoardSessionId().getId())
            );
        }

        boardData.setBoardSessions(boardSessionDatas);
//        List<CommittedFigureData> committedFigureDatas = new ArrayList<>();
//        for (CommittedFigure committedFigure: board.getCommittedFigures()) {
//            committedFigureDatas.add(new CommittedFigureData(committedFigure.getFigureId(), committedFigure.getZOrder()));
//        }
//        boardData.setCommittedFigures(committedFigureDatas);
        return boardData;
    }
}
