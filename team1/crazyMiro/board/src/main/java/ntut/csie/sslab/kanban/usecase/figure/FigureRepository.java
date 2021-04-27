package ntut.csie.sslab.kanban.usecase.figure;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;

import java.util.List;

public interface FigureRepository extends AbstractRepository<Figure, String> {
    List<Figure> getStickersByBoardId(String boardId);
}
