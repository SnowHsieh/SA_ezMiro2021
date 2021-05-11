package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.edit.fontsize.EditFontSizeOfStickyNoteInput;
import ntut.csie.selab.usecase.widget.edit.fontsize.EditFontSizeOfStickyNoteOutput;
import ntut.csie.selab.usecase.widget.edit.fontsize.EditFontSizeOfStickyNoteUseCase;
import org.junit.Assert;
import org.junit.Test;

public class EditFrontSizeOfStickyNoteUseCaseTest {
    @Test
    public void edit_front_size_of_sticky_note_should_succeed() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        String stickyNoteId = "1";
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", stickyNoteCoordinate);

        widgetRepository.add(stickyNote);
        DomainEventBus domainEventBus = new DomainEventBus();
        EditFontSizeOfStickyNoteUseCase editFrontSizeOfStickyNoteUseCase = new EditFontSizeOfStickyNoteUseCase(widgetRepository, domainEventBus);
        EditFontSizeOfStickyNoteInput input = new EditFontSizeOfStickyNoteInput();
        EditFontSizeOfStickyNoteOutput output = new EditFontSizeOfStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);
        input.setFontSize(18);

        // Act
        editFrontSizeOfStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(18, output.getFontSize());

    }
}
