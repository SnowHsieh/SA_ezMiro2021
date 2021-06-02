package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.Widget;

import java.util.Optional;

public interface LineRepository {
    void save(Line line);

    void delete(Line line);

    Optional<Widget> findById(final String lineId);
}
