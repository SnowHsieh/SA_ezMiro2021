package ntut.csie.sslab.miro.adapter.presenter.note;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.miro.usecase.note.NoteDto;
import java.util.List;

public class NoteViewModel implements ViewModel {
    private NoteDto note;
    private List<NoteDto> notes;

    public NoteDto getNote() {
        return note;
    }

    public void setNote(NoteDto note) {
        this.note = note;
    }

    public List<NoteDto> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDto> notes) {
        this.notes = notes;
    }
}