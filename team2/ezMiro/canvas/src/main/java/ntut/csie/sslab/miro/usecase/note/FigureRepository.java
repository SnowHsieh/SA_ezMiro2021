package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import java.util.List;

public interface FigureRepository extends AbstractRepository<Figure, String> {
    List<Figure> findByBoardId(String boardId);
}
