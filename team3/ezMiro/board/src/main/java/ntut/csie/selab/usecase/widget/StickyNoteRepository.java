package ntut.csie.selab.usecase.widget;


import ntut.csie.selab.entity.model.widget.Widget;

import java.util.Optional;

public interface StickyNoteRepository {
    void save(Widget stickyNote);

    void delete(Widget stickyNote);

    Optional<Widget> findById(final String stickyNoteId);
}
