package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepositoryPeer extends CrudRepository<NoteData, String> {
    List<NoteData> findByBoardId(String boardId);
}