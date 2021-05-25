package ntut.csie.selab.adapter.board;

import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardData;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardDataMapper;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.usecase.board.BoardRepository;
import java.util.Optional;

public class BoardRepositoryImpl implements BoardRepository {
    private BoardRepositoryPeer peer;

    public BoardRepositoryImpl(BoardRepositoryPeer boardRepositoryPeer) {
        this.peer = boardRepositoryPeer;
    }

    @Override
    public void save(Board board) {
        BoardData boardData = BoardDataMapper.domainToData(board);
        peer.save(boardData);
    }

    @Override
    public Optional<Board> findById(String boardId) {
        Optional<BoardData> boardData = peer.findById(boardId);
        if (boardData.isPresent()) {
            BoardData selectedBoardData = boardData.get();
            Board result = BoardDataMapper.DataToDomain(selectedBoardData);
            return Optional.of(result);
        }
        return Optional.empty();
    }
}
