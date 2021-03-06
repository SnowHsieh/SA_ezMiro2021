package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;

import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.BoardSession;
import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;

import java.util.ArrayList;
import java.util.List;

public class BoardMapper {

    public static Board transformToDomain(BoardData boardData) {
        Board board = new Board(boardData.getBoardId(), boardData.getBoardName());
        for (CommittedFigureData committedFigureData : boardData.getCommittedFigures()) {
            board.commitFigure(committedFigureData.getFigureId());
        }
        for (BoardSessionData boardSessionData : boardData.getBoardSessions()) {
            board.addBoardSession(new BoardSession(boardSessionData.getUserId(),
                    boardSessionData.getBoardId(),
                    boardSessionData.getBoardSessionId()));
        }
        board.clearDomainEvents();
        return board;
    }

    public static List<Board> transformToDomain(List<BoardData> boardDatas) {
        List<Board> boards = new ArrayList<>();
        for (BoardData boardData : boardDatas) {
            Board board = new Board(boardData.getBoardId(), boardData.getBoardName());
            for (CommittedFigureData committedFigureData : boardData.getCommittedFigures()) {
                board.commitFigure(committedFigureData.getFigureId());
            }
            for (BoardSessionData boardSessionData : boardData.getBoardSessions()) {
                board.addBoardSession(new BoardSession(boardSessionData.getUserId(),
                                                        boardSessionData.getBoardId(),
                                                        boardSessionData.getBoardSessionId()));
            }
            board.clearDomainEvents();
            boards.add(board);
        }
        return boards;
    }

    public static BoardData transformToData(Board board) {
        BoardData boardData = new BoardData(board.getBoardId(), board.getBoardName());
        List<BoardSessionData> boardSessionDatas = new ArrayList<>();
        for (BoardSession boardSession : board.getBoardSessions()) {
            boardSessionDatas.add(new BoardSessionData(boardSession.getUserId(),
                    boardSession.getBoardId(),
                    boardSession.getBoardSessionId()));
        }

        boardData.setBoardSessions(boardSessionDatas);
        List<CommittedFigureData> committedFigureDatas = new ArrayList<>();
        for (CommittedFigure committedFigure: board.getCommittedFigures()) {
            committedFigureDatas.add(new CommittedFigureData(committedFigure.getFigureId(), committedFigure.getZOrder()));
        }
        boardData.setCommittedFigures(committedFigureDatas);
        return boardData;
    }
}
