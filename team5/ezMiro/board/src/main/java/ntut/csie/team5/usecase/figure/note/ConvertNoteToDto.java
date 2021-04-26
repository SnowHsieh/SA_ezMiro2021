package ntut.csie.team5.usecase.figure.note;

import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.FigureDto;

public class ConvertNoteToDto {

    public static FigureDto transform(Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setBoardId(note.getBoardId());
        noteDto.setFigureId(note.getId());
        noteDto.setPosition(note.getPosition());
        noteDto.setColor(note.getColor());
        return noteDto;
    }
}
