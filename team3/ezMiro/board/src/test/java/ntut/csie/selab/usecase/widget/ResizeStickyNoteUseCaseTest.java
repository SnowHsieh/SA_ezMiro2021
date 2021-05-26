package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetRepositoryPeer;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.widget.resize.ResizeStickyNoteUseCase;
import ntut.csie.selab.usecase.widget.resize.ResizeStickyNoteInput;
import ntut.csie.selab.usecase.widget.resize.ResizeStickyNoteOutput;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes= JpaApplicationTest.class)
@Rollback(false)
public class ResizeStickyNoteUseCaseTest {

    @Autowired
    private WidgetRepositoryPeer widgetRepositoryPeer;

    @Test
    public void resize_sticky_note_should_succed() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl(widgetRepositoryPeer);
        DomainEventBus eventBus = new DomainEventBus();

        String stickyNoteId = "77946-45641-7546";
        Coordinate coordinate = new Coordinate(10, 10, 100, 100);
        Widget stickyNote = new StickyNote(stickyNoteId, "0", coordinate);
        widgetRepository.save(stickyNote);
        ResizeStickyNoteUseCase resizeStickyNoteUseCase = new ResizeStickyNoteUseCase(widgetRepository, eventBus);
        ResizeStickyNoteInput input = new ResizeStickyNoteInput();
        ResizeStickyNoteOutput output = new ResizeStickyNoteOutput();
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
