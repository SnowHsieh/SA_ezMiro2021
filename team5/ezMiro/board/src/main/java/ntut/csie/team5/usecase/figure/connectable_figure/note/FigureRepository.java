package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.team5.entity.model.figure.Figure;

import java.util.List;

public interface FigureRepository extends AbstractRepository<Figure, String> {

    List<Figure> getFiguresByBoardId(String boardId);
}
