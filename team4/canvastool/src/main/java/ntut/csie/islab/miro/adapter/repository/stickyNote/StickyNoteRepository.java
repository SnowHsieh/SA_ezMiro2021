package ntut.csie.islab.miro.adapter.repository.stickyNote;

import ntut.csie.islab.miro.entity.stickyNote.StickyNote;
import ntut.csie.islab.miro.usecase.stickyNote.CreateStickyNoteInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StickyNoteRepository {
    private List<StickyNote> stickyNoteList;

    public StickyNoteRepository() {
        this.stickyNoteList = new ArrayList<StickyNote>();
    }

    public void save(StickyNote stickyNote) {
        this.stickyNoteList.add(stickyNote);
    }

    public Optional<StickyNote> findById(UUID id) {
        return stickyNoteList.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }
}
