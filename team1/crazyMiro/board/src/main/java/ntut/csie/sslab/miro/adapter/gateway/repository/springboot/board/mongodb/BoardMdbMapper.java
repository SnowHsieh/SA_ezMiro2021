package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.mongodb;

import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardSession;
import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;

import java.util.ArrayList;
import java.util.List;

public class BoardMdbMapper {

    public static Board transformToDomain(BoardMdbData boardData) {
        Board board = new Board(boardData.getBoardId(), boardData.getBoardName());
        for (CommittedFigureMdbData committedFigureData : boardData.getCommittedFigures()) {
            board.commitFigure(committedFigureData.getFigureId());
        }
        for (BoardSessionMdbData boardSessionData : boardData.getBoardSessions()) {
            board.addBoardSession(new BoardSession(boardSessionData.getUserId(),
                    boardSessionData.getBoardId(),
                    boardSessionData.getBoardSessionId()));
        }
        board.clearDomainEvents();
        return board;
    }

    public static List<Board> transformToDomain(List<BoardMdbData> boardDatas) {
        List<Board> boards = new ArrayList<>();
        for (BoardMdbData boardData : boardDatas) {
            Board board = new Board(boardData.getBoardId(), boardData.getBoardName());
            for (CommittedFigureMdbData committedFigureData : boardData.getCommittedFigures()) {
                board.commitFigure(committedFigureData.getFigureId());
            }
            for (BoardSessionMdbData boardSessionData : boardData.getBoardSessions()) {
                board.addBoardSession(new BoardSession(boardSessionData.getUserId(),
                        boardSessionData.getBoardId(),
                        boardSessionData.getBoardSessionId()));
            }
            board.clearDomainEvents();
            boards.add(board);
        }
        return boards;
    }

    public static BoardMdbData transformToMdbData(Board board) {
        BoardMdbData boardData = new BoardMdbData(board.getBoardId(), board.getBoardName());
        List<BoardSessionMdbData> boardSessionDatas = new ArrayList<>();
        for (BoardSession boardSession : board.getBoardSessions()) {
            boardSessionDatas.add(new BoardSessionMdbData(boardSession.getUserId(),
                    boardSession.getBoardId(),
                    boardSession.getBoardSessionId()));
        }

        boardData.setBoardSessions(boardSessionDatas);
        List<CommittedFigureMdbData> committedFigureDatas = new ArrayList<>();
        for (CommittedFigure committedFigure: board.getCommittedFigures()) {
            committedFigureDatas.add(new CommittedFigureMdbData(committedFigure.getFigureId(), committedFigure.getZOrder()));
        }
        boardData.setCommittedFigures(committedFigureDatas);
        return boardData;
    }
}
