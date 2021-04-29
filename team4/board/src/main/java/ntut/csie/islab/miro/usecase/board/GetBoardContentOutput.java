package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.usecase.figure.FigureDto;
import ntut.csie.sslab.ddd.usecase.Output;

import java.util.List;
import java.util.UUID;

public interface GetBoardContentOutput extends Output {

    GetBoardContentOutput setBoardId(UUID boardId);

    UUID getBoardId();

    GetBoardContentOutput setFigures(List<FigureDto> figureDtos);

    List<FigureDto> getFigures();

}
