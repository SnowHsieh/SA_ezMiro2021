package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepositoryPeer extends CrudRepository<LineData, String> {

    List<LineData> findAllByHeadWidgetIdOrTailWidgetId(String headWidgetId, String tailWidgetId);
}
