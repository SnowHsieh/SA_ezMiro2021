package ntut.csie.islab.miro.adapter.presenter.getContent;

import ntut.csie.islab.miro.usecase.textFigure.TextFigureDto;
import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;

import java.util.List;
import java.util.UUID;

public class BoardContentViewModel implements ViewModel {
    private UUID boardId;
    private List<TextFigureDto> textFigureDtos;

    public UUID getBoardId() {
        return boardId;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    public List<TextFigureDto> getFigureDtos() {
        return textFigureDtos;
    }

    public void setFigureDtos(List<TextFigureDto> textFigureDtos) {
        this.textFigureDtos = textFigureDtos;
    }
}
