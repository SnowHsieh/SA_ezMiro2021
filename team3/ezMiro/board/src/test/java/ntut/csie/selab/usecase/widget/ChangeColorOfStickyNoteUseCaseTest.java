package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.edit.color.ChangeColorOfStickyNoteInput;
import ntut.csie.selab.usecase.widget.edit.color.ChangeColorOfStickyNoteOutput;
import ntut.csie.selab.usecase.widget.edit.color.ChangeColorOfStickyNoteUseCase;
import org.junit.Assert;
import org.junit.Test;

public class ChangeColorOfStickyNoteUseCaseTest {

    @Test
    public void change_color_of_sticky_note_should_succeed() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        String stickyNodeId = "1";
        Coordinate coordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNodeId, "0", coordinate);
        widgetRepository.add(stickyNote);
        DomainEventBus domainEventBus = new DomainEventBus();
        ChangeColorOfStickyNoteUseCase changeColorOfStickyNoteUseCase = new ChangeColorOfStickyNoteUseCase(widgetRepository, domainEventBus);
        ChangeColorOfStickyNoteInput input = new ChangeColorOfStickyNoteInput();
        ChangeColorOfStickyNoteOutput output = new ChangeColorOfStickyNoteOutput();
        input.setStickyNoteId(stickyNodeId);
        input.setStickyNoteColor("#555555");

        // Act
        changeColorOfStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals("#555555", output.getStickyNoteColor());
    }
}
