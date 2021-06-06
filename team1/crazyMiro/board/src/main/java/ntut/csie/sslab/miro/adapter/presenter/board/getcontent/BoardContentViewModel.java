package ntut.csie.sslab.miro.adapter.presenter.board.getcontent;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.miro.usecase.figure.ConnectionFigureDto;
import ntut.csie.sslab.miro.usecase.figure.line.LineDto;

import java.util.List;

public class BoardContentViewModel implements ViewModel {
    private String boardId;
    private List<ConnectionFigureDto> stickers;
    private List<LineDto> lines;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<ConnectionFigureDto> getStickers() {
        return stickers;
    }

    public void setStickers(List<ConnectionFigureDto> stickers) {
        this.stickers = stickers;
    }

    public List<LineDto> getLines() {
        return lines;
    }

    public void setLines(List<LineDto> lines) {
        this.lines = lines;
    }
}
