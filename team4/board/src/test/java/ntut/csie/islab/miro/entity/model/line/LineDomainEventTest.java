package ntut.csie.islab.miro.entity.model.line;

import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.entity.model.figure.line.event.LineCreatedDomainEvent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineDomainEventTest {

    @Test
    public void create_a_line_then_publishes_a_line_created_domain_event() {
        UUID boardId = UUID.randomUUID();
        List<Position> positionList = new ArrayList<>();

        int strokeWidth = 5;
        String color = "#000000";
        Figure line = new Line(boardId,positionList,strokeWidth,color);

        assertEquals(1, line.getDomainEvents().size());
        assertEquals(LineCreatedDomainEvent.class, line.getDomainEvents().get(0).getClass());

    }

}
