package ntut.csie.islab.team.entity;

import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.entity.model.board.event.FigureCommittedDomainEvent;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamDomainEventTest {
    private Team createTeam() {
        return new Team("teamName", UUID.randomUUID().toString());
    }

    @Test
    public void create_a_board_then_publishes_a_board_created_domain_event() {
        Team team = createTeam();
        assertEquals(1, team.getDomainEvents().size());
    }
}
