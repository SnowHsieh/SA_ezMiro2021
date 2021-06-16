package ntut.csie.selab.usecase.widget.stickynote;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
import ntut.csie.selab.usecase.widget.stickynote.edit.text.EditTextOfStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.edit.text.EditTextOfStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.edit.text.EditTextOfStickyNoteUseCase;
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
public class EditTextOfStickyNoteUseCaseTest {

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Test
    public void edit_text_of_sticky_note_should_succeed() {
        // Arrange
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        String stickyNoteId = "1";
        Position stickyNotePosition = new Position(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", stickyNotePosition);
        stickyNoteRepository.save(stickyNote);
        DomainEventBus domainEventBus = new DomainEventBus();
        EditTextOfStickyNoteUseCase editTextOfStickyNoteUseCase = new EditTextOfStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        EditTextOfStickyNoteInput input = new EditTextOfStickyNoteInput();
        EditTextOfStickyNoteOutput output = new EditTextOfStickyNoteOutput();
        input.setStickyNoteId(stickyNoteId);
        input.setText("modified text");

        // Act
        editTextOfStickyNoteUseCase.execute(input, output);

        // Assert
        Assert.assertEquals("modified text", output.getText());
        Assert.assertEquals(1, domainEventBus.getCount());
    }
}
