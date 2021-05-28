package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepositoryPeer extends CrudRepository<BoardData, String> {

}