package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.figure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FigureRepositoryPeer extends CrudRepository<FigureData, String> {
}
