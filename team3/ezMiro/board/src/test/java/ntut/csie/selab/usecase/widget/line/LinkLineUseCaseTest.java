package ntut.csie.selab.usecase.widget.line;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.widget.LineRepositoryImpl;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.widget.LineRepository;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
import ntut.csie.selab.usecase.widget.line.link.LinkLineInput;
import ntut.csie.selab.usecase.widget.line.link.LinkLineOutput;
import ntut.csie.selab.usecase.widget.line.link.LinkLineUseCase;
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
@ContextConfiguration(classes = JpaApplicationTest.class)
@Rollback(false)
public class LinkLineUseCaseTest {

    @Autowired
    private LineRepositoryPeer lineRepositoryPeer;

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Test
    public void link_line_should_succeed() {
        // Arrange

        DomainEventBus domainEventBus = new DomainEventBus();
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        LineRepository lineRepository = new LineRepositoryImpl(lineRepositoryPeer);
        LinkLineUseCase linkLineUseCase = new LinkLineUseCase(lineRepository, stickyNoteRepository, domainEventBus);
        LinkLineInput input = new LinkLineInput();
        LinkLineOutput output = new LinkLineOutput();
        String boardId = "boardId";
        String lineId = "lineId";
        String targetId = "targetId";
        String endPoint = "head"; // head or tail
        input.setBoardId(boardId);
        input.setLineId(lineId);
        input.setTargetId(targetId);
        input.setEndPoint(endPoint);

        Widget line = new Line(lineId, boardId, new Position(1, 1, 100, 1));
        lineRepository.save(line);
        Widget stickyNote = new StickyNote(targetId, boardId, new Position(2, 2, 200, 200));
        stickyNoteRepository.save(stickyNote);

        // Act
        linkLineUseCase.execute(input, output);

        // Assert
        Line actualLine = (Line) lineRepository.findById(output.getLineId()).get();
        Assert.assertEquals(boardId, output.getBoardId());
        Assert.assertEquals(targetId, output.getTargetId());
        Assert.assertTrue(lineRepository.findById(output.getLineId()).isPresent());
        Assert.assertEquals(lineId, actualLine.getId());
        Assert.assertEquals(targetId, actualLine.getHeadWidgetId());
        Assert.assertNull(actualLine.getTailWidgetId());
        Assert.assertEquals(1, domainEventBus.getCount());
    }
}
