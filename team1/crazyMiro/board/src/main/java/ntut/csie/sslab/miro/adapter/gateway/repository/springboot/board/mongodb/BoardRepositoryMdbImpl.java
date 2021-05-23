package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.mongodb;

import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardData;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardMapper;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardRepositoryMdbImpl implements BoardRepository {

    private BoardRepositoryMongoDbPeer peer;

    public BoardRepositoryMdbImpl(BoardRepositoryMongoDbPeer peer) {
        this.peer = peer;
    }

    @Override
    public List<Board> findAll() {
        List<BoardMdbData> boardDatas = new ArrayList();
        peer.findAll().forEach(boardDatas::add);
        return BoardMdbMapper.transformToDomain(boardDatas);
    }

    @Override
    public Optional<Board> findById(String boardId) {
        return peer.findById(boardId).map(BoardMdbMapper::transformToDomain);
    }

    @Override
    public void save(Board board) {
        peer.save(BoardMdbMapper.transformToMdbData(board));
    }

    @Override
    public void deleteById(String boardId) {
        peer.deleteById(boardId);
    }
}