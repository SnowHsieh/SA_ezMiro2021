package ntut.csie.islab.miro.adapter.repository.textFigure;

import ntut.csie.islab.miro.entity.model.textFigure.Position;
import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import java.util.*;
import static java.util.stream.Collectors.toList;

public class TextFigureRepository {
    private List<TextFigure> stickyNoteList;

    public TextFigureRepository() {
        this.stickyNoteList = new ArrayList<TextFigure>();
    }

    public void save(TextFigure stickyNote) {
        this.stickyNoteList.add(stickyNote);
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

    public void edit(UUID boardId, TextFigure stickyNote, String content, Style style) {
        stickyNote.setContent(content);
        stickyNote.setStyle(style);//todo boardId is not used ??
    }

    public void move(TextFigure stickyNote, Position newPosition) {
        stickyNote.setPosition(newPosition);
    }

    public void changeContent(TextFigure stickyNote, String content) {
        stickyNote.setContent(content);
    }

    public void changeColor(TextFigure stickyNote, String color) {
        stickyNote.getStyle().setColor(color);
    }

    public void resize(TextFigure stickyNote, double width, double height){
        stickyNote.getStyle().setWidth(width);
        stickyNote.getStyle().setHeight(height);
    }
}
