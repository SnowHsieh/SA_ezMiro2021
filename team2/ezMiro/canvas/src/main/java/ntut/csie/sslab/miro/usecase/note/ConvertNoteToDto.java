package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.Note;
import java.util.ArrayList;
import java.util.List;

public class ConvertNoteToDTO {
    public static NoteDTO transform(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setNoteId(note.getId());
        dto.setBoardId(note.getBoardId());
        dto.setDescription(note.getDescription());
        dto.setWidth(note.getWidth());
        dto.setHeight(note.getHeight());
        dto.setColor(note.getColor());
        dto.setCoordinate(note.getCoordinate());
        return dto;
    }

    public static List<NoteDTO> transform(List<Note> notes) {
        List<NoteDTO> noteDTOs = new ArrayList<>();
        for(Note note : notes) {
            noteDTOs.add(transform(note));
        }
        return noteDTOs;
    }
}