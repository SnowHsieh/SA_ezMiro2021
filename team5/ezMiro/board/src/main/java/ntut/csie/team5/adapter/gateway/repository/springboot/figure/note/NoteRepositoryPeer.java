package ntut.csie.team5.adapter.gateway.repository.springboot.figure.note;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepositoryPeer extends CrudRepository<NoteData, String> {
}
