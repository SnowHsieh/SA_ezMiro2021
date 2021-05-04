package ntut.csie.team5.usecase.figure;

import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.connectable_figure.note.ConvertNoteToDto;


public class ConvertConnectableFigureToDto extends FigureDto {

    public static FigureDto transform(Figure figure) {
        if (figure instanceof Note) {
            return ConvertNoteToDto.transform((Note) figure);
        }
        return null;
    }
}
