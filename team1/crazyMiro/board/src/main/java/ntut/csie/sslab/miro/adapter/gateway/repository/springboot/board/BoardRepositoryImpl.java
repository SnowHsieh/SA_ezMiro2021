package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;


import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardRepositoryImpl implements BoardRepository {

//    private List<Board> boards;
    private BoardRepositoryPeer peer;

    public BoardRepositoryImpl(BoardRepositoryPeer boardRepositoryPeer) {
//        boards = new ArrayList<>();
        this.peer = boardRepositoryPeer;
    }

    @Override
    public List<Board> findAll() {
//        return Collections.unmodifiableList(boards);
        List<BoardData> boardDatas = new ArrayList();
        peer.findAll().forEach(boardDatas::add);
        return BoardMapper.transformToDomain(boardDatas);
    }

    @Override
    public Optional<Board> findById(String boardId) {
//        return boards.stream().filter(x-> x.getBoardId().equals(boardId)).findFirst();
        return peer.findById(boardId).map(BoardMapper::transformToDomain);
    }

    @Override
    public void save(Board board) {
//        boards.add(board);
        peer.save(BoardMapper.transformToData(board));
    }

    @Override
    public void deleteById(String boardId) {
        peer.deleteById(boardId);
    }
}