package ntut.csie.sslab.miro.usecase.note.get;

import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.miro.usecase.note.NoteDTO;
import java.util.List;

public interface GetNoteOutput extends Output {
    NoteDTO getNote();

    void setNote(NoteDTO noteDto);

    List<NoteDTO> getNotes();

    void setNotes(List<NoteDTO> noteDto);
}
