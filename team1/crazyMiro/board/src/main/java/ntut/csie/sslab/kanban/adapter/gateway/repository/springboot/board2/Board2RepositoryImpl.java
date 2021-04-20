package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2;

import ntut.csie.sslab.kanban.entity.model.board2.Board2;
import ntut.csie.sslab.kanban.usecase.board2.Board2Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board2RepositoryImpl implements Board2Repository {

    private BoardRepositoryPeer peer;

    public Board2RepositoryImpl(BoardRepositoryPeer peer){
        this.peer = peer;
    }

    @Override
    public List<Board2> findAll() {
        List<BoardData> boardDatas = new ArrayList();
        peer.findAll().forEach(boardDatas::add);
        return BoardMapper.transformToDomain(boardDatas);
    }

    @Override
    public Optional<Board2> findById(String id) {
        return peer.findById(id).map(BoardMapper::transformToDomain);
    }

    @Override
    public void save(Board2 board2) {
        BoardData data = BoardMapper.transformToData(board2);
        peer.save(data);
    }

    @Override
    public void deleteById(String id) {
        peer.deleteById(id);
    }

    @Override
    public List<Board2> getBoardsByUserId(String userId) {
        return null;
    }

    @Override
    public List<Board2> getBoardsByProjectId(String projectId) {
        return null;
    }
}
