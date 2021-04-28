package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.note;

import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.entity.model.note.NoteBuilder;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class FigureRepositoryImpl implements FigureRepository {
    private Map<String, Figure> figures = new HashMap<>();

    public FigureRepositoryImpl() {
        Note note1 = NoteBuilder.newInstance()
                .coordinate(new Coordinate(new Point(100, 100)))
                .boardId("boardId")
                .color("#C9DF56")
                .description("BoardId\nCoordinate")
                .width(100)
                .height(100)
                .build();
        Note note2 = NoteBuilder.newInstance()
                .coordinate(new Coordinate(new Point(220, 100)))
                .boardId("boardId")
                .color("#6CD8FA")
                .description("Create\nNote")
                .width(100)
                .height(100)
                .build();
        Note note3 = NoteBuilder.newInstance()
                .coordinate(new Coordinate(new Point(340, 100)))
                .boardId("boardId")
                .color("#FF9D48")
                .description("Note\nCreated")
                .width(100)
                .height(100)
                .build();
        Note note4 = NoteBuilder.newInstance()
                .coordinate(new Coordinate(new Point(280, 20)))
                .boardId("boardId")
                .color("#FFF9B1")
                .description("Figure")
                .width(100)
                .height(100)
                .build();
        Note note5 = NoteBuilder.newInstance()
                .coordinate(new Coordinate(new Point(150, 180)))
                .boardId("boardId")
                .color("#FEF445")
                .description("User")
                .width(100)
                .height(100)
                .build();
        this.figures.put(note1.getId(), note1);
        this.figures.put(note2.getId(), note2);
        this.figures.put(note3.getId(), note3);
        this.figures.put(note4.getId(), note4);
        this.figures.put(note5.getId(), note5);
    }

    @Override
    public List<Figure> findAll() {
        return this.figures.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Figure> findById(String figureId) {
        return Optional.of(figures.get(figureId));
    }

    @Override
    public void save(Figure data) {
        figures.put(data.getId(), data);
    }

    @Override
    public void deleteById(String figureId) {
        figures.remove(figureId);
    }

    @Override
    public List<Figure> findByBoardId(String boardId) {
        return figures.values().stream().filter(x -> x.getBoardId().equals(boardId)).collect(Collectors.toList());
    }
}
