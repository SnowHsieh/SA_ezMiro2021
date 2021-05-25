package ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepositoryMongoDbPeer extends MongoRepository<BoardMdbData, String> {
}
