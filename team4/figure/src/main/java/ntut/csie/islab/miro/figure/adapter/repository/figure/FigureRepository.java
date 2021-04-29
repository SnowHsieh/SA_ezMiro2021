package ntut.csie.islab.miro.figure.adapter.repository.figure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ntut.csie.islab.miro.figure.entity.model.figure.Style;
import ntut.csie.islab.miro.figure.entity.model.figure.Figure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import org.apache.commons.io.FileUtils;

import static java.util.stream.Collectors.toList;

import org.json.JSONException;
import org.json.JSONObject;

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
