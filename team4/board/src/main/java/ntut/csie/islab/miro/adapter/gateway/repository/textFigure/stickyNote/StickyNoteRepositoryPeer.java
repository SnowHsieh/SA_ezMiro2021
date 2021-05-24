package ntut.csie.islab.miro.adapter.gateway.repository.textFigure.stickyNote;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickyNoteRepositoryPeer extends CrudRepository<StickyNoteData, String> {
}
