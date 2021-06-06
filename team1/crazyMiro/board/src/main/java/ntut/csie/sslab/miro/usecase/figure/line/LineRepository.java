package ntut.csie.sslab.miro.usecase.figure.line;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.miro.entity.model.figure.Line;

import java.util.List;

public interface LineRepository extends AbstractRepository<Line, String> {
    List<Line> getLineByBoardId(String boardId);

    List<Line> findByFigureId(String figureId);
}
