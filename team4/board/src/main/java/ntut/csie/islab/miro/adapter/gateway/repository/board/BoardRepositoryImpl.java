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
        List<BoardData> boardDatas = new ArrayList();
        peer.findAll().forEach(x -> boardDatas.add(x));
        return BoardMapper.transformToDomain(boardDatas);
    }

    @Override
    public Optional<Board> findById(UUID boardId) {
        System.out.println("findById: ");
        System.out.println(peer.findById("110b1535-dd79-406e-aada-91235e2af94d"));
        return peer.findById(boardId.toString()).map(BoardMapper::transformToDomain);
    }

    @Override
    public void save(Board board){
        System.out.println("BoardRepositoryImpl save");
        System.out.println("Name:" + board.getBoardName());
        System.out.println(peer);
        peer.save(BoardMapper.transformToData(board));
    }

    @Override
    public void deleteById(UUID boardId) {
        peer.deleteById(boardId.toString());
    }
}
