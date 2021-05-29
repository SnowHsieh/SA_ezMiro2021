package ntut.csie.islab.miro.adapter.presenter.getcontent;

import ntut.csie.islab.miro.usecase.textfigure.FigureDto;
import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;

import java.util.List;
import java.util.UUID;

public class BoardContentViewModel implements ViewModel {
    private UUID boardId;
    private List<FigureDto> figureDtos;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public List<FigureDto> getFigureDtos() {
        return figureDtos;
    }

    public void setFigureDtos(List<FigureDto> figureDtos) {
        this.figureDtos = figureDtos;
    }
}
