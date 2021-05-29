package ntut.csie.islab.miro.adapter.gateway.repository.figure.line;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepositoryPeer extends CrudRepository<LineData, String> {
}
