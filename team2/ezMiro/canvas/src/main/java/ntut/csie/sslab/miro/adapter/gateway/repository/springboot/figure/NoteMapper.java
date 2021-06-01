package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure;

import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.entity.model.note.Note;
import java.util.ArrayList;
import java.util.List;

public class NoteMapper {
    public static List<Note> transformToDomain(List<NoteData> datas) {
        List<Note> notes = new ArrayList<>();
        datas.forEach(x -> notes.add(transformToDomain(x)));
        return notes;
    }

    public static Note transformToDomain(NoteData data) {
        Coordinate coordinate = new Coordinate(data.getX(), data.getY());
        Note note = new Note(data.getBoardId(), data.getNoteId(), data.getDescription(), data.getColor(), coordinate, data.getWidth(), data.getHeight());
        note.clearDomainEvents();
        return note;
    }

    public static NoteData transformToData(Note note) {
        NoteData data = new NoteData(
                note.getBoardId(),
                note.getId(),
                note.getDescription(),
                note.getColor(),
                note.getCoordinate(),
                note.getWidth(),
                note.getHeight());
        return data;
    }
}