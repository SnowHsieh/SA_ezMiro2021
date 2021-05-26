package ntut.csie.selab.adapter.board;

import ntut.csie.selab.adapter.gateway.repository.springboot.board.*;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.CommittedWidgetData;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.CommittedWidgetDataKey;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.CommittedWidget;
import ntut.csie.selab.usecase.board.BoardAssociationRepository;

import java.util.List;
import java.util.Optional;

public class BoardAssociationRepositoryImpl implements BoardAssociationRepository {
    private BoardRepositoryPeer boardRepositoryPeer;
    private CommittedWidgetRepositoryPeer commitedWidgetRepositoryPeer;

    public BoardAssociationRepositoryImpl(BoardRepositoryPeer boardRepositoryPeer, CommittedWidgetRepositoryPeer committedWidgetRepositoryPeer) {
        this.boardRepositoryPeer = boardRepositoryPeer;
        this.commitedWidgetRepositoryPeer = committedWidgetRepositoryPeer;
    }

    @Override
    public void save(Board board) {
        BoardData boardData = BoardDataMapper.domainToData(board);
        boardRepositoryPeer.save(boardData);
    }

    @Override
    public void saveCommittedWidget(String boardId, String widgetId) {
        Optional<BoardData> boardData = boardRepositoryPeer.findById(boardId);
        if (boardData.isPresent()) {
            BoardData selectedBoard = boardData.get();
            Long zOrder = commitedWidgetRepositoryPeer.countByBoard(selectedBoard);
            CommittedWidgetData committedWidgetData = new CommittedWidgetData(selectedBoard.getBoardId(), widgetId, zOrder.intValue());
            commitedWidgetRepositoryPeer.save(committedWidgetData);
        }
    }

    @Override
    public void saveAllCommittedWidget(List<CommittedWidget> committedWidgets) {
        List<CommittedWidgetData> widgetDatas = CommittedWidgetDataMapper.domainToData(committedWidgets);
        commitedWidgetRepositoryPeer.saveAll(widgetDatas);
    }

    @Override
    public void deleteCommittedWidget(String boardId, String widgetId) {
        Optional<BoardData> boardData = boardRepositoryPeer.findById(boardId);
        if (boardData.isPresent()) {
            BoardData selectedBoard = boardData.get();
            commitedWidgetRepositoryPeer.deleteById(new CommittedWidgetDataKey(selectedBoard.getBoardId(), widgetId));
        }
    }

    @Override
    public Optional<Board> findById(String boardId) {
        Optional<BoardData> boardData = boardRepositoryPeer.findById(boardId);
        if (boardData.isPresent()) {
            BoardData selectedBoardData = boardData.get();
            Board result = BoardDataMapper.DataToDomain(selectedBoardData);
            return Optional.of(result);
        }
        return Optional.empty();
    }
}
