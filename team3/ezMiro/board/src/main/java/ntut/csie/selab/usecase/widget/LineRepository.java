package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.widget.Line;

import java.util.Optional;

public interface LineRepository {
    void save(Line line);

    void delete(Line line);

    Optional<Line> findById(final String lineId);
}
