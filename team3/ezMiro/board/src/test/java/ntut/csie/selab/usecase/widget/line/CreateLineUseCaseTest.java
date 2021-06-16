package ntut.csie.selab.usecase.widget.line;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineRepositoryPeer;
import ntut.csie.selab.adapter.widget.LineRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.widget.LineRepository;
import ntut.csie.selab.usecase.widget.line.create.CreateLineInput;
import ntut.csie.selab.usecase.widget.line.create.CreateLineOutput;
import ntut.csie.selab.usecase.widget.line.create.CreateLineUseCase;
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
@ContextConfiguration(classes = JpaApplicationTest.class)
@Rollback(false)
public class CreateLineUseCaseTest {

    @Autowired
    private LineRepositoryPeer lineRepositoryPeer;

    @Test
    public void create_line_should_succeed() {
        // Arrange
        DomainEventBus domainEventBus = new DomainEventBus();
        LineRepository lineRepository = new LineRepositoryImpl(lineRepositoryPeer);
        CreateLineUseCase createLineUseCase = new CreateLineUseCase(lineRepository, domainEventBus);
        CreateLineInput input = new CreateLineInput();
        CreateLineOutput output = new CreateLineOutput();
        String boardId = "1";
        input.setBoardId(boardId);
        input.setPosition(new Position(1, 1, 2, 2));

        // Act
        createLineUseCase.execute(input, output);

        // Assert
        Assert.assertEquals("1", output.getBoardId());
        Assert.assertEquals(new Point(1, 1), output.getPosition().getTopLeft());
        Assert.assertEquals(new Point(2, 2), output.getPosition().getBottomRight());
        Assert.assertTrue(lineRepository.findById(output.getLineId()).isPresent());
        Assert.assertEquals(output.getLineId(), lineRepository.findById(output.getLineId()).get().getId());
        Assert.assertEquals(1, domainEventBus.getCount());
    }

}
