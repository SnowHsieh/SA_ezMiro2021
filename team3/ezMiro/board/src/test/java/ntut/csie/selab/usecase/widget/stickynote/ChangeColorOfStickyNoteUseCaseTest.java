package ntut.csie.selab.usecase.widget.stickynote;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetRepositoryPeer;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import ntut.csie.selab.usecase.widget.stickynote.edit.color.ChangeColorOfStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.edit.color.ChangeColorOfStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.edit.color.ChangeColorOfStickyNoteUseCase;
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
public class ChangeColorOfStickyNoteUseCaseTest {

    @Autowired
    private WidgetRepositoryPeer widgetRepositoryPeer;

    @Test
    public void change_color_of_sticky_note_should_succeed() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl(widgetRepositoryPeer);
        String stickyNodeId = "1";
        Coordinate coordinate = new Coordinate(1, 1, 2, 2);
        Widget stickyNote = new StickyNote(stickyNodeId, "0", coordinate);
        widgetRepository.save(stickyNote);
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
