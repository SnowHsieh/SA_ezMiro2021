package ntut.csie.islab.miro.adapter.presenter;

import ntut.csie.islab.miro.adapter.presenter.getContent.BoardContentViewModel;
import ntut.csie.islab.miro.usecase.textFigure.TextFigureDto;
import ntut.csie.islab.miro.usecase.board.GetBoardContentOutput;
import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetBoardContentPresenter extends Result implements Presenter<BoardContentViewModel>, GetBoardContentOutput {
    private UUID boardId;
    private List<TextFigureDto> textFigureDtos;

    public GetBoardContentPresenter() {
        this.textFigureDtos = new ArrayList<>();
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
    public GetBoardContentOutput setFigures(List<TextFigureDto> textFigureDtos){
        this.textFigureDtos = textFigureDtos;
        return this;
    }
    @Override
    public List<TextFigureDto> getFigures(){
        return this.textFigureDtos;

    }

    @Override
    public BoardContentViewModel buildViewModel() {
        BoardContentViewModel boardContentViewModel = new BoardContentViewModel();
        boardContentViewModel.setBoardId(boardId);
        boardContentViewModel.setFigureDtos(textFigureDtos);
        return boardContentViewModel;
    }
}
