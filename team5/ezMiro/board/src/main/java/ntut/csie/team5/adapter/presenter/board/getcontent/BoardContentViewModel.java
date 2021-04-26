package ntut.csie.team5.adapter.presenter.board.getcontent;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.team5.usecase.figure.FigureDto;

import java.util.List;

public class BoardContentViewModel implements ViewModel {

    private String boardId;
    private List<FigureDto> figureDtos;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<FigureDto> getFigureDtos() {
        return figureDtos;
    }

    public void setFigureDtos(List<FigureDto> figureDtos) {
        this.figureDtos = figureDtos;
    }
}
