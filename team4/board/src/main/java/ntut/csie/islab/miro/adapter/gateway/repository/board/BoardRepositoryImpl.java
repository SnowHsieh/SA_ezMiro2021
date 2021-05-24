package ntut.csie.islab.miro.adapter.gateway.repository.board;

import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.usecase.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class BoardRepositoryImpl implements BoardRepository {
    private BoardRepositoryPeer peer;
    public BoardRepositoryImpl(BoardRepositoryPeer peer){
        this.peer = peer;
    }

    @Override
    public List<Board> findAll() {
        List<BoardData> boardDataList = new ArrayList();
        peer.findAll().forEach(x -> boardDataList.add(x));
        return BoardMapper.transformToDomain(boardDataList);
    }

    @Override
    public Optional<Board> findById(UUID boardId) {
        // whenever call this , it will rebuild board again.
        return peer.findById(boardId.toString()).map(BoardMapper::transformToDomain);
    }

    @Override
    public void save(Board board){
        peer.save(BoardMapper.transformToData(board));
    }

    @Override
    public void deleteById(UUID boardId) {
        peer.deleteById(boardId.toString());
    }
}
