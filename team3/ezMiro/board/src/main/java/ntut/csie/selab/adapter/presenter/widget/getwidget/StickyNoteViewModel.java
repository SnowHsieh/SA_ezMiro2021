package ntut.csie.selab.adapter.presenter.widget.getwidget;

import ntut.csie.selab.usecase.widget.StickyNoteDto;

public class StickyNoteViewModel {
    StickyNoteDto stickyNoteDto;

    public StickyNoteViewModel(StickyNoteDto stickyNoteDto) {
        this.stickyNoteDto = stickyNoteDto;
    }

    public StickyNoteDto getWidgetDto() { return stickyNoteDto; }
}
