package ntut.csie.team5.usecase.board.getcontent;

import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.usecase.figure.FigureDto;

import java.util.List;

public interface GetBoardContentOutput extends Output {

    String getBoardId();

    GetBoardContentOutput setBoardId(String boardId);

    List<FigureDto> getFigureDtos();

    GetBoardContentOutput setFigures(List<FigureDto> figureDtos);
}
