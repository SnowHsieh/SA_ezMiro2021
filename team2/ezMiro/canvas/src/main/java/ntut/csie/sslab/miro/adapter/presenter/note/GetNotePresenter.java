package ntut.csie.sslab.miro.adapter.presenter.note;

import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Result;
import ntut.csie.sslab.miro.usecase.note.NoteDto;
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
    public NoteDto getNote() {
        return viewModel.getNote();
    }

    @Override
    public void setNote(NoteDto noteDto) {
        viewModel.setNote(noteDto);
    }

    @Override
    public List<NoteDto> getNotes() {
        return viewModel.getNotes();
    }

    @Override
    public void setNotes(List<NoteDto> noteDtos) {
        viewModel.setNotes(noteDtos);
    }
}