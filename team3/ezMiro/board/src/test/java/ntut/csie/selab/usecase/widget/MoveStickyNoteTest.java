package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.move.MoveStickyNoteInput;
import ntut.csie.selab.usecase.widget.move.MoveStickyNoteOutput;
import ntut.csie.selab.usecase.widget.move.MoveStickyNoteUseCase;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class MoveStickyNoteTest {

    @Test
    public void move_sticky_note_should_succeed() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        String stickyNoteId = "1";
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", stickyNoteCoordinate);
        widgetRepository.add(stickyNote);
        DomainEventBus domainEventBus = new DomainEventBus();
        MoveStickyNoteUseCase moveStickyNoteUseCase = new MoveStickyNoteUseCase(widgetRepository, domainEventBus);
        MoveStickyNoteInput input = new MoveStickyNoteInput();
        MoveStickyNoteOutput output = new MoveStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);
        input.setCoordinate(new Coordinate(4, 4, 5, 5));

        // Act
        moveStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(new Point(4, 4), output.getCoordinate().getTopLeft());
        Assert.assertEquals(new Point(5, 5), output.getCoordinate().getBottomRight());
        Assert.assertEquals(2, domainEventBus.getCount());
    }
}
