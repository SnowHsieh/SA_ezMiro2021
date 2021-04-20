package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.card;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CardRepositoryPeer extends CrudRepository<CardData, String> {

    @Query(value = "SELECT * FROM card as c JOIN tag_in_card ON c.card_id = tag_in_card.card_id WHERE tag_in_card.tag_id = :tagId",
            nativeQuery = true)
    List<CardData> getCardsByTagId(@Param("tagId") String tagId);

}