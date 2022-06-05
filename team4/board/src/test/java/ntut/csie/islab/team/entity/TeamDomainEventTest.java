package ntut.csie.islab.team.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamDomainEventTest {
    private Team createTeam() {
        return new Team("teamName", UUID.randomUUID().toString());
    }

    @Test
    public void create_a_team_then_publishes_a_team_created_domain_event() {
        Team team = createTeam();
        assertEquals(1, team.getDomainEvents().size());
    }
}
