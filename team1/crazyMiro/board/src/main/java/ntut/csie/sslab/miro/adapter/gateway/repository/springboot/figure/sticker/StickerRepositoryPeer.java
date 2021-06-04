package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.sticker;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerRepositoryPeer extends CrudRepository<StickerData, String> {
}
