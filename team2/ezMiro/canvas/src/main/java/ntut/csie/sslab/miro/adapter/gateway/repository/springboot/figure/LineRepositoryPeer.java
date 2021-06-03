package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LineRepositoryPeer extends CrudRepository<LineData, String> {
    List<LineData> findByBoardId(String boardId);
}