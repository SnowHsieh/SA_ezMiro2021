package ntut.csie.team5.usecase.figure.note;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;

import java.util.List;

public interface FigureRepository extends AbstractRepository<Figure, String> {

    List<Figure> getFiguresByBoardId(String boardId);
}
