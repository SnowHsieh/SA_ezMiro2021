package ntut.csie.team5.adapter.gateway.repository.springboot.board;

import ntut.csie.team5.entity.model.board.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardMapper {

    public static List<Board> transformToDomain(List<BoardData> datas) {
        List<Board> boards = new ArrayList<>();
        datas.forEach(boardData -> boards.add(transformToDomain(boardData)));
        return boards;
    }

    public static Board transformToDomain(BoardData data) {
        Board board = new Board(data.getBoardId(), data.getName(), data.getProjectId());
        for (CommittedFigureData committedFigureData: data.getCommittedFigureDatas()) {
            board.addFigure(committedFigureData.getFigureId());
        }
        for (BoardSessionData boardSessionData: data.getBoardSessionDatas()) {
            board.addBoardSession(boardSessionData.getBoardSessionId(), boardSessionData.getUserId());
        }
        for (CursorData cursorData: data.getCursorDatas()) {
            board.addCursor(cursorData.getUserId(), cursorData.getPositionX(), cursorData.getPositionX());
        }
        board.clearDomainEvents();
        return board;
    }

    public static BoardData transformToData(Board board) {
        BoardData boardData = new BoardData(
            board.getProjectId(),
            board.getBoardId(),
            board.getName()
        );
        boardData.setCommittedFigureDatas(CommittedFigureMapper.
                transformToData(board.getCommittedFigures()));
        boardData.setBoardSessionDatas(BoardSessionMapper.
                transformToData(board.getBoardSessions()));
        boardData.setCursorDatas(CursorMapper.
                transformToData(board.getCursors()));
        return boardData;
    }
}
