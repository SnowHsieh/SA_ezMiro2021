package ntut.csie.selab.usecase.widget.stickynote;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import ntut.csie.selab.usecase.widget.stickynote.edit.fontsize.EditFontSizeOfStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.edit.fontsize.EditFontSizeOfStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.edit.fontsize.EditFontSizeOfStickyNoteUseCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes= JpaApplicationTest.class)
@Rollback(false)
public class EditFrontSizeOfStickyNoteUseCaseTest {

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Test
    public void edit_front_size_of_sticky_note_should_succeed() {
        // Arrange
        WidgetRepository widgetRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        String stickyNoteId = "1";
        Coordinate stickyNoteCoordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", stickyNoteCoordinate);

        widgetRepository.save(stickyNote);
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
