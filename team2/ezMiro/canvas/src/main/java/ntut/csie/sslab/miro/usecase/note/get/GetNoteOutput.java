package ntut.csie.sslab.miro.usecase.note.get;

import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.miro.usecase.note.NoteDto;
import java.util.List;

public interface GetNoteOutput extends Output {
    NoteDto getNote();

    void setNote(NoteDto noteDto);

    List<NoteDto> getNotes();

    void setNotes(List<NoteDto> noteDto);
}
