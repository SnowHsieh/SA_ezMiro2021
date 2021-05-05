package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.miro.entity.model.note.Note;
import java.util.ArrayList;
import java.util.List;

public class ConvertNoteToDto {
    public static NoteDto transform(Note note) {
        NoteDto dto = new NoteDto();
        dto.setNoteId(note.getId());
        dto.setBoardId(note.getBoardId());
        dto.setDescription(note.getDescription());
        dto.setWidth(note.getWidth());
        dto.setHeight(note.getHeight());
        dto.setColor(note.getColor());
        dto.setCoordinate(note.getCoordinate());
        dto.setDisplayOrder(note.getDisplayOrder());
        return dto;
    }

    public static List<NoteDto> transform(List<Note> notes) {
        List<NoteDto> noteDtos = new ArrayList<>();
        for(Note note : notes) {
            noteDtos.add(transform(note));
        }
        return noteDtos;
    }
}