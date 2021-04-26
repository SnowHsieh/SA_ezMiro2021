package ntut.csie.team5.adapter.presenter.board.getcontent;

import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Result;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentOutput;
import ntut.csie.team5.usecase.figure.FigureDto;

import java.util.List;

public class GetBoardContentPresenter extends Result implements Presenter<BoardContentViewModel>, GetBoardContentOutput {

    private String boardId;
    private List<FigureDto> figureDtos;

    @Override
    public BoardContentViewModel buildViewModel() {
        BoardContentViewModel boardContentViewModel = new BoardContentViewModel();
        boardContentViewModel.setBoardId(boardId);
        boardContentViewModel.setFigureDtos(figureDtos);
        return boardContentViewModel;
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public GetBoardContentOutput setBoardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    @Override
    public List<FigureDto> getFigureDtos() {
        return figureDtos;
    }

    @Override
    public GetBoardContentOutput setFigures(List<FigureDto> figureDtos) {
        this.figureDtos = figureDtos;
        return this;
    }
}
