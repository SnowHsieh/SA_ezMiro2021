package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BoardRepositoryPeer extends CrudRepository<BoardData, String> {
}