package ntut.csie.sslab.kanban.usecase.card;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.entity.model.card.CardType;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.card.edit.description.ChangeCardDescriptionInput;
import ntut.csie.sslab.kanban.usecase.card.edit.description.ChangeCardDescriptionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChangeCardDescriptionUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }


    @Test
    public void should_succeed_when_change_card_description() {

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

        ChangeCardDescriptionUseCase changeCardDescriptionUseCase = newChangeCardDescriptionUseCase();
        ChangeCardDescriptionInput input = changeCardDescriptionUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setCardId(firstCardId);
        input.setNewDescription("EditedDescription");
        input.setBoardId(boardId);
        input.setUserId(userId);
        input.setUsername(username);

        changeCardDescriptionUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        Card card = cardRepository.findById(output.getId()).get();
        assertEquals("EditedDescription", card.getDescription());
    }
}