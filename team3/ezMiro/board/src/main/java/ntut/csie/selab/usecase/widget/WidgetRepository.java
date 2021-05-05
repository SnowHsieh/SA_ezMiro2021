package ntut.csie.selab.usecase.widget;


import ntut.csie.selab.entity.model.widget.Widget;

import java.util.Optional;

public interface WidgetRepository {
    void add(Widget widget);

    void delete(Widget widget);

    Optional<Widget> findById(final String stickyNoteId);
}
