package ntut.csie.selab.usecase.widget.line;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.LineRepositoryPeer;
import ntut.csie.selab.adapter.widget.LineRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.widget.LineRepository;
import ntut.csie.selab.usecase.widget.line.delete.DeleteLineInput;
import ntut.csie.selab.usecase.widget.line.delete.DeleteLineOutput;
import ntut.csie.selab.usecase.widget.line.delete.DeleteLineUseCase;
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
public class DeleteLineUseCaseTest {

    @Autowired
    private LineRepositoryPeer lineRepositoryPeer;

    @Test
    public void delete_line_should_succeed() {
        // Arrange
        DomainEventBus domainEventBus = new DomainEventBus();
        LineRepository lineRepository = new LineRepositoryImpl(lineRepositoryPeer);

        String boardId = "0";
        String lineId = "1";
        Coordinate lineCoordinate = new Coordinate(1, 1, 2, 2);
        Widget line = new Line(lineId, boardId, lineCoordinate);
        lineRepository.save(line);

        DeleteLineUseCase deleteLineUseCase = new DeleteLineUseCase(lineRepository, domainEventBus);
        DeleteLineInput input = new DeleteLineInput();
        DeleteLineOutput output = new DeleteLineOutput();
        input.setLineId(lineId);

        // Act
        deleteLineUseCase.execute(input, output);

        // Assert
        Assert.assertNotNull(output.getLineId());
        Assert.assertFalse(lineRepository.findById(output.getLineId()).isPresent());
    }
}
