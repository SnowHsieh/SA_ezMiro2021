package ntut.csie.islab.miro.adapter.gateway.repository.textfigure.stickynote;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickyNoteRepositoryPeer extends CrudRepository<StickyNoteData, String> {
}
