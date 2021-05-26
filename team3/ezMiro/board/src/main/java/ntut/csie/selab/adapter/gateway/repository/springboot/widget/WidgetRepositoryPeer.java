package ntut.csie.selab.adapter.gateway.repository.springboot.widget;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepositoryPeer extends CrudRepository<WidgetData, String>{
}
