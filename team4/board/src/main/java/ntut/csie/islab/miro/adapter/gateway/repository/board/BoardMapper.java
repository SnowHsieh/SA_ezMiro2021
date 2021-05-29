package ntut.csie.islab.miro.adapter.gateway.repository.board;


import ntut.csie.islab.miro.entity.model.board.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoardMapper {

    public static Board transformToDomain(BoardData boardData) {
        Board board = new Board(UUID.fromString(boardData.getTeamId()),
                UUID.fromString(boardData.getBoardId()), boardData.getBoardName());

        for (CommittedFigureData committedFigureData : boardData.getCommittedFigures()) {
            board.commitFigure(
                    UUID.fromString(committedFigureData.getFigureId()),
                    FigureTypeEnum.fromInteger(committedFigureData.getFigureType())
            );

        }

        List<BoardSession> boardSessionList = new ArrayList<>();
        for (BoardSessionData boardSessionData : boardData.getBoardSessions()) {
            boardSessionList.add(new BoardSession(UUID.fromString(boardSessionData.getBoardId()),
                            UUID.fromString(boardSessionData.getUserId()),
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
        List<BoardSessionData> boardSessionDataList = new ArrayList<>();
        for (BoardSession boardSession : board.getBoardSessionList()) {
            boardSessionDataList.add(new BoardSessionData(boardSession.getBoardId().toString(),
                    boardSession.getUserId().toString(),
                    boardSession.getBoardSessionId().getId())
            );
        }

        boardData.setBoardSessions(boardSessionDataList);
        List<CommittedFigureData> committedFigureDatas = new ArrayList<>();

        List<CommittedFigure> committedFigures = board.getCommittedFigures();
        for (int i =0 ; i < committedFigures.size() ; i++) {
            CommittedFigureData item = new CommittedFigureData(
                    committedFigures.get(i).getFigureId().toString(),
                    i,
                    committedFigures.get(i).getFigureType().ordinal()
            );
            committedFigureDatas.add(item);
        }
        boardData.setCommittedFigures(committedFigureDatas);
        return boardData;
    }
}
