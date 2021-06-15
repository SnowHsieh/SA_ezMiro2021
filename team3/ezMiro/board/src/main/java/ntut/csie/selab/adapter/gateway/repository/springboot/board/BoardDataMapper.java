package ntut.csie.selab.adapter.gateway.repository.springboot.board;

import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.entity.model.board.Cursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BoardDataMapper {
    public static BoardData domainToData(Board board) {
        List<CommittedWidget> committedWidgets = board.getCommittedWidgets();
        return new BoardData(board.getId(), board.getTeamId(), board.getBoardName(), CommittedWidgetDataMapper.domainToData(committedWidgets));
    }

    public static Board DataToDomain(BoardData selectedBoardData) {
        Board board = new Board(selectedBoardData.getBoardId(), selectedBoardData.getTeamId(), selectedBoardData.getBoardName());
        board.setCommittedWidgets(new ArrayList<>(CommittedWidgetDataMapper.dataToDomain(selectedBoardData.getCommittedWidgets())));
        return board;
    }
}
