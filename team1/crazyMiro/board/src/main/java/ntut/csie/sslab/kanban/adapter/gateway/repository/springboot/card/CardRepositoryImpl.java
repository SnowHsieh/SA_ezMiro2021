package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.card;

import com.google.common.collect.Lists;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.usecase.card.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardRepositoryImpl implements CardRepository {


    private CardRepositoryPeer peer;

    public CardRepositoryImpl(CardRepositoryPeer peer){
        this.peer = peer;
    }

    @Override
    public List<Card> findAll() {

        return CardMapper.transformToDomain(Lists.newArrayList(peer.findAll()));
    }

    @Override
    public Optional<Card> findById(String id) {
        Card card = null;

        Optional<CardData> data = peer.findById(id);
        if (data.isPresent()){
            card = CardMapper.transformToDomain(data.get());
        }

        return Optional.ofNullable(card);
    }

    @Override
    public void save(Card card) {
        CardData data = CardMapper.transformToData(card);

        peer.save(CardMapper.transformToData(card));
    }

    @Override
    public void deleteById(String id) {
        peer.deleteById(id);
    }


    @Override
    public List<Card> getCardsByLaneId(String swimLaneId) {
        List<Card> result = new ArrayList<>();

        for(CardData each : peer.findAll()){
            if (each.getLaneId().equalsIgnoreCase(swimLaneId)){
                result.add(CardMapper.transformToDomain(each));
            }
        }

        return result;
    }

    @Override
    public List<Card> getCardsByCategoryId(String categoryId) {
        return null;
    }

    @Override
    public List<Card> getCardsByWorkflowId(String workflowId) {
        List<Card> result = new ArrayList<>();

        for(CardData each : peer.findAll()){
            if (each.getWorkflowId().equalsIgnoreCase(workflowId)){
                result.add(CardMapper.transformToDomain(each));
            }
        }

        return result;
    }

    @Override
    public List<Card> getCardsByBoardId(String boardId) {
        List<Card> result = new ArrayList<>();

        for(CardData each : peer.findAll()){
            if (each.getBoardId().equalsIgnoreCase(boardId)){
                result.add(CardMapper.transformToDomain(each));
            }
        }

        return result;
    }

    @Override
    public List<Card> findCardsByTagId(String tagId) {
        List<Card> result = new ArrayList<>();
        for(CardData each: peer.getCardsByTagId(tagId)){
            result.add(CardMapper.transformToDomain(each));
        }
        return result;
    }

}
