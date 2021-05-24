package ntut.csie.islab.miro.adapter.gateway.repository.board;

import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.usecase.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class BoardRepositoryImpl implements BoardRepository {
    private BoardRepositoryPeer peer;
    List<Board> ActiveBoardList;
    public BoardRepositoryImpl(BoardRepositoryPeer peer){
        ActiveBoardList = new ArrayList<>();
        this.peer = peer;
    }

    @Override
    public List<Board> findAll() {
        List<BoardData> boardDataList = new ArrayList();
        peer.findAll().forEach(x -> boardDataList.add(x));
        ActiveBoardList = BoardMapper.transformToDomain(boardDataList);
        return ActiveBoardList;
    }

    @Override
    public Optional<Board> findById(UUID boardId) {
        // whenever call this , it will rebuild board again.
        Optional<Board> exitsBoard = ActiveBoardList.stream().filter(s -> s.getBoardId().equals(boardId)).findFirst();
        if(exitsBoard.isPresent()){
            return exitsBoard;
        }else{
            Optional<Board> foundBoard = peer.findById(boardId.toString()).map(BoardMapper::transformToDomain);
            if(foundBoard.isPresent()){
                ActiveBoardList.add(foundBoard.get());
            }
            return foundBoard;
        }
    }

    @Override
    public void save(Board board){
        Optional<Board> exitsBoard = ActiveBoardList.stream().filter(s -> s.getBoardId().equals(board.getBoardId())).findFirst();
        if(exitsBoard.isPresent()){
            ActiveBoardList.remove(exitsBoard.get());
        }
        ActiveBoardList.add(board);
        peer.save(BoardMapper.transformToData(board));
    }

    @Override
    public void deleteById(UUID boardId) {
        ActiveBoardList.removeIf(board -> board.getBoardId().equals(boardId));
        peer.deleteById(boardId.toString());
    }
}
