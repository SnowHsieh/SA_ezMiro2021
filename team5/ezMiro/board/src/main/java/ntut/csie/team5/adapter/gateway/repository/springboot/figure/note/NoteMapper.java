package ntut.csie.team5.adapter.gateway.repository.springboot.figure.note;

import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.note.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteMapper {

    public static List<Note> transformToDomain(List<NoteData> datas) {
        List<Note> notes = new ArrayList<>();
        datas.forEach(noteData -> notes.add(transformToDomain(noteData)));
        return notes;
    }

    public static Note transformToDomain(NoteData data) {
        Note note = new Note(
            data.getNoteId(),
            data.getBoardId(),
            data.getLeftTopPositionX(),
            data.getLeftTopPositionY(),
            data.getHeight(),
            data.getWidth(),
            data.getColor(),
            FigureType.NOTE
        );
        note.setText(data.getText());
        note.clearDomainEvents();
        return note;
    }

    public static NoteData transformToData(Note note) {
        NoteData noteData = new NoteData(
            note.getId(),
            note.getBoardId(),
            note.getLeftTopPositionX(),
            note.getLeftTopPositionY(),
            note.getWidth(),
            note.getHeight(),
            note.getColor(),
            note.getText()
        );
        return noteData;
    }
}
