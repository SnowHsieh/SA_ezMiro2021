package ntut.csie.islab.miro.figure.adapter.repository.figure;

import ntut.csie.islab.miro.figure.entity.figure.Style;
import ntut.csie.islab.miro.figure.entity.figure.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FigureRepository {
    private List<Figure> stickyNoteList;

    public FigureRepository() {
        this.stickyNoteList = new ArrayList<Figure>();
    }

    public void save(Figure stickyNote) {
        this.stickyNoteList.add(stickyNote);
    }

    public Optional<Figure> findById(UUID id) {
        return stickyNoteList.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }

    public void delete(Figure stickyNote) {
        stickyNoteList.remove(stickyNote);
    }

    public void edit(Figure stickyNote, String content, Style style) {
        stickyNote.setContent(content);
        stickyNote.setStyle(style);
    }
}
