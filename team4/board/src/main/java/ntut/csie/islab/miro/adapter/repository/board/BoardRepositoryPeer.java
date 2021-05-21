package ntut.csie.islab.miro.adapter.repository.board;

import ntut.csie.islab.miro.entity.model.board.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRepositoryPeer extends CrudRepository<BoardData, UUID> {
}
