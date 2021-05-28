package ntut.csie.islab.miro.usecase.board.getboardcontent;

import ntut.csie.islab.miro.usecase.textfigure.TextFigureDto;
import ntut.csie.sslab.ddd.usecase.Output;

import java.util.List;
import java.util.UUID;

public interface GetBoardContentOutput extends Output {

    GetBoardContentOutput setBoardId(UUID boardId);

    UUID getBoardId();

    GetBoardContentOutput setFigures(List<TextFigureDto> textFigureDtos);

    List<TextFigureDto> getFigures();

}
