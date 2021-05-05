package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.resize.ResizeStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.resize.ResizeStickyNoteUseCaseInput;
import ntut.csie.selab.usecase.widget.resize.ResizeStickyNoteUseCaseOutput;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class ResizeStickyNoteUseCaseTest {
    @Test
    public void resize_sticky_note_should_succed() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        DomainEventBus eventBus = new DomainEventBus();

        String stickyNoteId = "77946-45641-7546";
        Coordinate coordinate = new Coordinate(10, 10, 100, 100);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", coordinate);
        widgetRepository.add(stickyNote);
        ResizeStickyNoteUseCase resizeStickyNoteUseCase = new ResizeStickyNoteUseCase(widgetRepository, eventBus);
        ResizeStickyNoteUseCaseInput input = new ResizeStickyNoteUseCaseInput();
        ResizeStickyNoteUseCaseOutput output = new ResizeStickyNoteUseCaseOutput();
        input.setStickyNoteId(stickyNoteId);
        input.setCoordinate(new Coordinate(10, 10, 50, 50));

        // Act
        resizeStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(new Point(10, 10), output.getCoordinate().getTopLeft());
        Assert.assertEquals(new Point(50, 50), output.getCoordinate().getBottomRight());
        Assert.assertEquals(2, eventBus.getCount());

    }
}
