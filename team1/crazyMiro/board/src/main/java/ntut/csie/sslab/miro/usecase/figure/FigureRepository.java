package ntut.csie.sslab.miro.usecase.figure;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.miro.entity.model.figure.ConnectionFigure;
import ntut.csie.sslab.miro.entity.model.figure.Figure;

import java.util.List;

public interface FigureRepository extends AbstractRepository<ConnectionFigure, String> {
    List<ConnectionFigure> getStickersByBoardId(String boardId);

    List<ConnectionFigure> getFiguresByBoardId(String boardId);
}
