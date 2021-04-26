package ntut.csie.team5.usecase.figure;

import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.note.NoteDto;

import java.util.ArrayList;
import java.util.List;

public class ConvertFiguresToDto {

    public static FigureDto transform(Figure figure) {
        if (figure instanceof Note) {
            Note note = (Note) figure;
            NoteDto dto = new NoteDto();
            dto.setBoardId(figure.getBoardId());
            dto.setFigureId(figure.getId());
            dto.setX(note.getPosition().x);
            dto.setY(note.getPosition().y);
            return dto;
        } else {
            FigureDto dto = new FigureDto();
            dto.setBoardId(figure.getBoardId());
            dto.setFigureId(figure.getId());
            return dto;
        }
    }

    public static List<FigureDto> transform(List<Figure> figures) {
        List<FigureDto> figureDtos = new ArrayList<>();
        for (Figure figure : figures) {
            figureDtos.add(transform(figure));
        }
        return figureDtos;
    }
}
