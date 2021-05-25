package ntut.csie.sslab.miro.adapter.presenter.note;

import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Result;
import ntut.csie.sslab.miro.usecase.note.NoteDTO;
import ntut.csie.sslab.miro.usecase.note.get.GetNoteOutput;
import java.util.List;

public class GetNotePresenter extends Result implements Presenter<NoteViewModel>, GetNoteOutput {
    private NoteViewModel viewModel;

    public GetNotePresenter(){
        viewModel = new NoteViewModel();
    }

    @Override
    public NoteViewModel buildViewModel() {
        return viewModel;
    }

    @Override
    public NoteDTO getNote() {
        return viewModel.getNote();
    }

    @Override
    public void setNote(NoteDTO noteDto) {
        viewModel.setNote(noteDto);
    }

    @Override
    public List<NoteDTO> getNotes() {
        return viewModel.getNotes();
    }

    @Override
    public void setNotes(List<NoteDTO> noteDTOS) {
        viewModel.setNotes(noteDTOS);
    }
}