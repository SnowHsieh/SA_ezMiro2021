package ntut.csie.islab.miro.adapter.gateway.repository.textfigure;

import ntut.csie.islab.miro.entity.model.textfigure.TextFigure;

import java.util.*;
import static java.util.stream.Collectors.toList;

public class TextFigureRepository {
    private List<TextFigure> stickyNoteList;

    public TextFigureRepository() {
        this.stickyNoteList = new ArrayList<TextFigure>();
    }

    public void save(TextFigure stickyNote) {
        stickyNoteList.removeIf(figure -> figure.getFigureId().equals(stickyNote.getFigureId()));
        stickyNoteList.add(stickyNote);

    }

    public Optional<TextFigure> findById(UUID boardId, UUID figureId) {

        return stickyNoteList.stream()
                .filter(s -> s.getBoardId().equals(boardId))
                .filter(s -> s.getId().equals(figureId))
                .findFirst();
    }

    public List<TextFigure> findFiguresByBoardId(UUID boardId) {
        return stickyNoteList.stream()
                .filter(s -> s.getBoardId().equals(boardId))
                .collect(toList());
    }

    public void delete(TextFigure stickyNote) {
        stickyNoteList.remove(stickyNote);
    }

}
