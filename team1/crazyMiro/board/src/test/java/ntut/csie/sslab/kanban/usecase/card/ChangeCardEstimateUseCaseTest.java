package ntut.csie.sslab.kanban.usecase.card;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.entity.model.card.CardType;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.card.edit.estimate.ChangeCardEstimateInput;
import ntut.csie.sslab.kanban.usecase.card.edit.estimate.ChangeCardEstimateUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChangeCardEstimateUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();

        boardId = "board id for change card estimate";
        workflowId = "workflow id for change card estimate";
        rootStageId = "root stage id for change card estimate";
        userId = "user id for change card estimate";
        username = "Teddy";
    }

    @Test
    public void should_succeed_when_change_card_estimate() {

        String firstCardId = createCard(
                userId,
                workflowId,
                rootStageId,
                "firstCard",
                "s",
                "notes",
                "2020/03/01",
                CardType.General.toString(),
                username,
                boardId);

        String newEstimate = "xl";
        ChangeCardEstimateUseCase changeCardEstimateUseCase = newChangeCardEstimateUseCase();

        ChangeCardEstimateInput input = changeCardEstimateUseCase.newInput();
        input.setCardId(firstCardId);
        input.setNewEstimate(newEstimate);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        changeCardEstimateUseCase.execute(input, output);

        assertEquals(firstCardId, output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        Card card = cardRepository.findById(output.getId()).get();
        assertEquals(newEstimate, card.getEstimate());
    }
}