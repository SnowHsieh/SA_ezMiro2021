package ntut.csie.sslab.kanban.usecase.card;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.entity.model.card.CardType;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.card.edit.deadline.ChangeCardDeadlineInput;
import ntut.csie.sslab.kanban.usecase.card.edit.deadline.ChangeCardDeadlineUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeCardDeadlineUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
        boardId = "board id for change card deadline";
        workflowId = "workflow id for change card deadline";
        rootStageId = "root stage id for change card deadline";
        userId = "user id for change card deadline";
        username = "Teddy";
    }

    @Test
    public void should_succeed_when_change_card_deadline() {

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

        String newDeadline = "2020/03/02";
        ChangeCardDeadlineUseCase changeCardDeadlineUseCase = newChangeCardDeadlineUseCase();

        ChangeCardDeadlineInput input = changeCardDeadlineUseCase.newInput();
        input.setCardId(firstCardId);
        input.setNewDeadline(newDeadline);
        input.setUserId(userId);
        input.setUsername(username);
        input.setBoardId(boardId);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        changeCardDeadlineUseCase.execute(input, output);

        assertEquals(firstCardId, output.getId());
        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        Card card = cardRepository.findById(output.getId()).get();
        assertEquals(newDeadline, card.getDeadline());
    }
}