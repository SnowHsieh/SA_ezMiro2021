package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.line;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepositoryPeer extends CrudRepository<LineData, String> {
}
