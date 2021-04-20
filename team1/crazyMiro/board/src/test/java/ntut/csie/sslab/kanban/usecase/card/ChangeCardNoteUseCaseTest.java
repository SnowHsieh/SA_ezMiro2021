package ntut.csie.sslab.kanban.usecase.card;


import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.card.Card;
import ntut.csie.sslab.kanban.entity.model.card.CardType;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import ntut.csie.sslab.kanban.usecase.card.edit.note.ChangeCardNoteInput;
import ntut.csie.sslab.kanban.usecase.card.edit.note.ChangeCardNoteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChangeCardNoteUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();

        boardId = "board id for delete card";
        workflowId = "workflow id for delete card";
        rootStageId = "root stage id for delete card";
        userId = "user id for delete card";
        username = "Teddy";
    }

    @Test
    public void should_succeed_when_change_card_notes() {

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

        String newNotes = "newNotes";
        ChangeCardNoteUseCase changeCardNoteUseCase = newChangeCardNotesUseCase();

        ChangeCardNoteInput input = changeCardNoteUseCase.newInput();
        input.setCardId(firstCardId);
        input.setNewNotes(newNotes);
        input.setUserId(userId);
        input.setUsername(userId);
        input.setBoardId(boardId);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        changeCardNoteUseCase.execute(input, output);

        assertEquals(firstCardId, output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());
        Card card = cardRepository.findById(output.getId()).get();
        assertEquals(newNotes, card.getNotes());
    }

}