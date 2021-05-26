package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.ddd.usecase.AbstractRepository;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.note.Note;
import java.util.List;
import java.util.Optional;

public interface FigureRepository extends AbstractRepository<Figure, String> {
    List<Figure> findByBoardId(String boardId);

    Optional<Note> findNoteById(String noteId);

    List<Note> findAllNotes();
}