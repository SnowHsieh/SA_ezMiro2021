package ntut.csie.sslab.kanban.usecase.card;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.entity.model.card.CardType;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.card.edit.type.ChangeCardTypeInput;
import ntut.csie.sslab.kanban.usecase.card.edit.type.ChangeCardTypeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChangeCardTypeUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();

        workflowId = "workflow id for change card type";
        rootStageId = "root stage id for change card type";
        boardId = "board id for change card type";
        userId = "user id for change card type";
        username = "Teddy";
    }


    @Test
    public void should_succeed_when_change_card_type() {

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

        String newType = CardType.Expedite.toString();
        ChangeCardTypeUseCase changeCardTypeUseCase = newChangeCardTypeUseCase();

        ChangeCardTypeInput input = changeCardTypeUseCase.newInput();
        input.setCardId(firstCardId);
        input.setNewType(newType);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        changeCardTypeUseCase.execute(input, output);

        assertEquals(firstCardId, output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
        Card card = cardRepository.findById(output.getId()).get();
        assertEquals(newType, card.getType().toString());
    }

}