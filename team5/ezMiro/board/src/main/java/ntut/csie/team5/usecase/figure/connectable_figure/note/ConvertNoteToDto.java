package ntut.csie.team5.usecase.figure.connectable_figure.note;

import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.FigureDto;

public class ConvertNoteToDto {

    public static FigureDto transform(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setBoardId(note.getBoardId());
        noteDto.setFigureId(note.getId());
        noteDto.setLeftTopPosition(note.getLeftTopPosition());
        noteDto.setHeight(note.getHeight());
        noteDto.setWidth(note.getWidth());
        noteDto.setColor(note.getColor());
        noteDto.setFigureType(note.getFigureType().toString());
        return noteDto;
    }
}
