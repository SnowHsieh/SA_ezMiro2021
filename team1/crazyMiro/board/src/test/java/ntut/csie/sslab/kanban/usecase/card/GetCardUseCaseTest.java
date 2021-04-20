package ntut.csie.sslab.kanban.usecase.card;


import ntut.csie.sslab.kanban.adapter.presenter.card.get.GetCardPresenter;
import ntut.csie.sslab.kanban.entity.model.card.CardType;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.card.get.GetCardInput;
import ntut.csie.sslab.kanban.usecase.card.get.GetCardUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GetCardUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void should_return_card_read_model_when_get_card_by_id() {

        String firstCardId = createCard(
                userId,
                workflowId,
                rootStageId,
                "firstCard",
                "estimate",
                "notes",
                "2020/03/01",
                CardType.General.toString(),
                username,
                boardId);

        GetCardUseCase getCardUseCase = newGetCardByIdUseCase();
        GetCardInput input = (GetCardInput) getCardUseCase;
        input.setCardId(firstCardId);

        GetCardPresenter output = new GetCardPresenter();
        getCardUseCase.execute(input, output);

        assertEquals(userId, output.getCard().getUserId());
        assertEquals(firstCardId, output.getCard().getCardId());
        assertEquals(rootStageId, output.getCard().getLaneId());
        assertEquals("firstCard", output.getCard().getDescription());
        assertEquals("estimate", output.getCard().getEstimate());
        assertEquals("notes", output.getCard().getNotes());
        assertEquals("2020/03/01",  output.getCard().getDeadline());
        assertEquals(CardType.General, output.getCard().getType());

    }
}