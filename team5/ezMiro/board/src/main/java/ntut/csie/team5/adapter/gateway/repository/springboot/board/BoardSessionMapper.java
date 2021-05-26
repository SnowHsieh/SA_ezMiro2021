package ntut.csie.team5.adapter.gateway.repository.springboot.board;

import ntut.csie.team5.entity.model.board.BoardSession;

import java.util.ArrayList;
import java.util.List;

public class BoardSessionMapper {

    public static BoardSessionData transformToData(BoardSession boardSession) {
        BoardSessionData boardSessionData = new BoardSessionData(
            boardSession.boardSessionId(),
            boardSession.boardId(),
            boardSession.userId()
        );
        return boardSessionData;
    }

    public static List<BoardSessionData> transformToData(List<BoardSession> boardSessions) {
        List<BoardSessionData> boardSessionDatas = new ArrayList<>();
        boardSessions.forEach(boardSession -> {
            boardSessionDatas.add(transformToData(boardSession));
        });
        return boardSessionDatas;
    }
}
