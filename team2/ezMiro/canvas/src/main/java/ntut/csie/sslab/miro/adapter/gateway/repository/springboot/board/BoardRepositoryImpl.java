package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;

import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardRepositoryImpl implements BoardRepository {

    private BoardRepositoryPeer peer;

    public BoardRepositoryImpl(BoardRepositoryPeer peer){
        this.peer = peer;
    }

    @Override
    public List<Board> findAll() {
        List<BoardData> boardDatas = new ArrayList();
        peer.findAll().forEach(boardDatas::add);
        return BoardMapper.transformToDomain(boardDatas);
    }

    @Override
    public Optional<Board> findById(String id) {
        return peer.findById(id).map(BoardMapper::transformToDomain);
    }

    @Override
    public void save(Board board) {
        BoardData data = BoardMapper.transformToData(board);
        peer.save(data);
    }

    @Override
    public void deleteById(String id) {
        peer.deleteById(id);
    }

    @Override
    public void deleteAll() {
        peer.deleteAll();
    }
}