package ntut.csie.islab.miro.adapter.repository.figure;

import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.Style;
import java.util.*;
import static java.util.stream.Collectors.toList;

public class FigureRepository {
    private List<Figure> stickyNoteList;

    public FigureRepository() {
        this.stickyNoteList = new ArrayList<Figure>();
    }

    public void save(Figure stickyNote) {
        this.stickyNoteList.add(stickyNote);
    }

    public Optional<Figure> findById(UUID boardId, UUID figureId) {

        return stickyNoteList.stream()
                .filter(s -> s.getBoardId().equals(boardId))
                .filter(s -> s.getId().equals(figureId))
                .findFirst();
    }

    public List<Figure> findFiguresByBoardId(UUID boardId) {
        return stickyNoteList.stream()
                .filter(s -> s.getBoardId().equals(boardId))
                .collect(toList());

    }

    public void delete(Figure stickyNote) {
        stickyNoteList.remove(stickyNote);
    }

    public void edit(UUID boardId, Figure stickyNote, String content, Style style) {
        stickyNote.setContent(content);
        stickyNote.setStyle(style);
    }

}