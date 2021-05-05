package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.edit.layer.EditZIndexOfStickyNoteInput;
import ntut.csie.selab.usecase.widget.edit.layer.EditZIndexOfStickyNoteOutput;
import ntut.csie.selab.usecase.widget.edit.layer.EditZIndexOfStickyNoteUseCase;
import org.junit.Assert;
import org.junit.Test;

public class EditZIndexOfStickyNoteUseCaseTest {

    @Test
    public void edit_zIndex_of_sticky_note_should_succeed() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        String stickyNoteId = "1";
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", stickyNoteCoordinate);
        widgetRepository.add(stickyNote);
        stickyNote.clearDomainEvents();

        DomainEventBus domainEventBus = new DomainEventBus();
        EditZIndexOfStickyNoteUseCase editZIndexOfStickyNoteUseCase = new EditZIndexOfStickyNoteUseCase(widgetRepository, domainEventBus);
        EditZIndexOfStickyNoteInput input = new EditZIndexOfStickyNoteInput();
        EditZIndexOfStickyNoteOutput output = new EditZIndexOfStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);
        input.setzIndex(1);

        // Act
        editZIndexOfStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals("1", output.getStickyNoteId());
        Assert.assertEquals(1, output.getzIndex());
        Assert.assertEquals(1, domainEventBus.getCount());
    }
}
