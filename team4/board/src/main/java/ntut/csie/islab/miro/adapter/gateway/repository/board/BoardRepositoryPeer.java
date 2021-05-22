package ntut.csie.islab.miro.adapter.gateway.repository.board;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardRepositoryPeer extends CrudRepository<BoardData, String> {

}
