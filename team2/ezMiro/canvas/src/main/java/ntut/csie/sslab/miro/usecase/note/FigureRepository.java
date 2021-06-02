package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.Note;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import java.util.List;
import java.util.Optional;

public interface FigureRepository extends AbstractRepository<Figure, String> {
    List<Figure> findByBoardId(String boardId);

    Optional<Note> findNoteById(String noteId);

    Optional<Line> findLineById(String lineId);

    List<Note> findAllNotes();

    List<Line> findAllLines();
}