package ntut.csie.islab.miro.adapter.presenter;

import ntut.csie.islab.miro.adapter.presenter.getContent.BoardContentViewModel;
import ntut.csie.islab.miro.figure.usecase.figure.FigureDto;
import ntut.csie.islab.miro.usecase.board.GetBoardContentOutput;
import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetBoardContentPresenter extends Result implements Presenter<BoardContentViewModel>, GetBoardContentOutput {
    private UUID boardId;
    private List<FigureDto> figureDtos;

    public GetBoardContentPresenter() {
        this.figureDtos = new ArrayList<>();
    }

    @Override
    public GetBoardContentOutput setBoardId(UUID boardId){
        this.boardId = boardId;
        return this;
    }
    @Override
    public UUID getBoardId() {
        return  this.boardId;
    }
    @Override
    public GetBoardContentOutput setFigures(List<FigureDto> figureDtos){
        this.figureDtos = figureDtos;
        return this;
    }
    @Override
    public List<FigureDto> getFigures(){
        return this.figureDtos;

    }

    @Override
    public BoardContentViewModel buildViewModel() {
        BoardContentViewModel boardContentViewModel = new BoardContentViewModel();
        boardContentViewModel.setBoardId(boardId);
        boardContentViewModel.setFigureDtos(figureDtos);
        return boardContentViewModel;
    }
}
