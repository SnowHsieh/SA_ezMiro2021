package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.line.Line;

import java.util.List;

public interface LineRepository extends AbstractRepository<Line, String> {
    List<Line> getLineByBoardId(String boardId);
}
