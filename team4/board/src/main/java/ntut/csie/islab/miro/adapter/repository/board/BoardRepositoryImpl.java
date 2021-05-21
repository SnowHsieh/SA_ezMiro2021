package ntut.csie.islab.miro.adapter.repository.board;

import ntut.csie.islab.miro.entity.model.board.Board;

import java.util.Optional;
import java.util.UUID;


public class BoardRepositoryImpl implements BoardRepository{
    private BoardRepositoryPeer peer;

    public BoardRepositoryImpl(BoardRepositoryPeer peer){
        this.peer = peer;
    }

    @Override
    public Optional<Board> findById(UUID boardId) {
        return this.peer.findById(boardId);
    }

    @Override
    public void save(Board board){
        this.peer.save(board);
    }
}
