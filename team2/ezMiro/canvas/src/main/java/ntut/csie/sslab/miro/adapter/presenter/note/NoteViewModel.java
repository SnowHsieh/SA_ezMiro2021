package ntut.csie.sslab.miro.adapter.presenter.note;

import ntut.csie.sslab.ddd.adapter.presenter.ViewModel;
import ntut.csie.sslab.miro.usecase.note.NoteDTO;
import java.util.List;

public class NoteViewModel implements ViewModel {
    private NoteDTO note;
    private List<NoteDTO> notes;

    public NoteDTO getNote() {
        return note;
    }

    public void setNote(NoteDTO note) {
        this.note = note;
    }

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }
}