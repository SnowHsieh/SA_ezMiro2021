package ntut.csie.selab.usecase.board;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.board.CommittedWidgetRepositoryPeer;
import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.domain.MockFactory;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetInput;
import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetOutput;
import ntut.csie.selab.usecase.board.edit.zorder.ChangeZOrderOfWidgetUseCase;
import ntut.csie.selab.usecase.eventHandler.NotifyBoard;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteInput;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteOutput;
import ntut.csie.selab.usecase.widget.stickynote.create.CreateStickyNoteUseCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes= JpaApplicationTest.class)
@Rollback(true)
public class ChangeZOrderOfWidgetUseCaseTest {

    @Autowired
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

    @Autowired
    private CommittedWidgetRepositoryPeer committedWidgetRepositoryPeer;

    @Test
    public void change_z_order_of_sticky_note_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);

        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepository, domainEventBus);
        domainEventBus.register(notifyBoard);
        String boardId = UUID.randomUUID().toString();
        Board board = MockFactory.createBoard(boardId);
        boardRepository.save(board);

        List<String> generatedWidgetIds = generateSequenceOfStickyNoteIn(boardId, domainEventBus);

        ChangeZOrderOfWidgetInput input = new ChangeZOrderOfWidgetInput();
        input.setBoardId(boardId);
        input.setWidgetId(generatedWidgetIds.get(0));
        input.setZOrder(2);
        ChangeZOrderOfWidgetOutput output = new ChangeZOrderOfWidgetOutput();

        ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase = new ChangeZOrderOfWidgetUseCase(boardRepository, domainEventBus);

        // Act
        changeZOrderOfWidgetUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(generatedWidgetIds.get(0), output.getWidgetId());
        Assert.assertEquals(2, output.getZOrder());

        // round 2
        input.setBoardId(boardId);
        input.setWidgetId(generatedWidgetIds.get(1));
        input.setZOrder(2);

        changeZOrderOfWidgetUseCase.execute(input, output);

        board = boardRepository.findById(boardId).get();
        Assert.assertEquals(generatedWidgetIds.get(1), output.getWidgetId());
        Assert.assertEquals(2, output.getZOrder());
        Assert.assertEquals(1, board.getCommittedWidgetBy(generatedWidgetIds.get(0)).get().getZOrder());
        Assert.assertEquals(2, board.getCommittedWidgetBy(generatedWidgetIds.get(1)).get().getZOrder());
        Assert.assertEquals(0, board.getCommittedWidgetBy(generatedWidgetIds.get(2)).get().getZOrder());
    }

    @Test
    public void change_z_order_of_sticky_note_to_neighbor_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        String boardId = UUID.randomUUID().toString();
        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepository, domainEventBus);
        domainEventBus.register(notifyBoard);
        Board board = MockFactory.createBoard(boardId);
        boardRepository.save(board);

        List<String> generatedWidgetIds = generateSequenceOfStickyNoteIn(boardId, domainEventBus);

        ChangeZOrderOfWidgetInput input = new ChangeZOrderOfWidgetInput();
        input.setBoardId(boardId);
        input.setWidgetId(generatedWidgetIds.get(1));
        input.setZOrder(2);
        ChangeZOrderOfWidgetOutput output = new ChangeZOrderOfWidgetOutput();

        ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase = new ChangeZOrderOfWidgetUseCase(boardRepository, domainEventBus);

        // Act
        changeZOrderOfWidgetUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(generatedWidgetIds.get(1), output.getWidgetId());
        Assert.assertEquals(2, output.getZOrder());
    }

    @Test
    public void change_z_order_of_sticky_note_revert_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepository, domainEventBus);
        domainEventBus.register(notifyBoard);
        String boardId = UUID.randomUUID().toString();
        Board board = MockFactory.createBoard(boardId);
        boardRepository.save(board);

        List<String> generatedWidgetIds = generateSequenceOfStickyNoteIn(boardId, domainEventBus);

        ChangeZOrderOfWidgetInput input = new ChangeZOrderOfWidgetInput();
        input.setBoardId(boardId);
        input.setWidgetId(generatedWidgetIds.get(2));
        input.setZOrder(0);
        ChangeZOrderOfWidgetOutput output = new ChangeZOrderOfWidgetOutput();

        ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase = new ChangeZOrderOfWidgetUseCase(boardRepository, domainEventBus);

        // Act
        changeZOrderOfWidgetUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(generatedWidgetIds.get(2), output.getWidgetId());
        Assert.assertEquals(0, output.getZOrder());
    }

    @Test
    public void change_z_order_of_sticky_note_to_neighbor_revert_should_succeed() {
        // Arrange
        BoardRepository boardRepository = new BoardRepositoryImpl(boardRepositoryPeer);
        DomainEventBus domainEventBus = new DomainEventBus();
        NotifyBoard notifyBoard = new NotifyBoard(boardRepository, domainEventBus);
        domainEventBus.register(notifyBoard);
        String boardId = UUID.randomUUID().toString();
        Board board = MockFactory.createBoard(boardId);
        boardRepository.save(board);

        List<String> generatedWidgetIds = generateSequenceOfStickyNoteIn(boardId, domainEventBus);

        ChangeZOrderOfWidgetInput input = new ChangeZOrderOfWidgetInput();
        input.setBoardId(boardId);
        input.setWidgetId(generatedWidgetIds.get(2));
        input.setZOrder(1);
        ChangeZOrderOfWidgetOutput output = new ChangeZOrderOfWidgetOutput();

        ChangeZOrderOfWidgetUseCase changeZOrderOfWidgetUseCase = new ChangeZOrderOfWidgetUseCase(boardRepository, domainEventBus);

        // Act
        changeZOrderOfWidgetUseCase.execute(input, output);

        // Assert
        Assert.assertEquals(generatedWidgetIds.get(2), output.getWidgetId());
        Assert.assertEquals(1, output.getZOrder());
    }

    private List<String> generateSequenceOfStickyNoteIn(String boardId, DomainEventBus domainEventBus) {
        List<String> generatedWidgetIds = new ArrayList<>();
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);

        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        CreateStickyNoteInput createStickyNoteInput = new CreateStickyNoteInput();
        CreateStickyNoteOutput createStickyNoteOutput = new CreateStickyNoteOutput();
        createStickyNoteInput.setBoardId(boardId);
        createStickyNoteInput.setPosition(new Position(1, 1, 1, 1));

        createStickyNoteUseCase.execute(createStickyNoteInput, createStickyNoteOutput);
        generatedWidgetIds.add(createStickyNoteOutput.getStickyNoteId());
        createStickyNoteUseCase.execute(createStickyNoteInput, createStickyNoteOutput);
        generatedWidgetIds.add(createStickyNoteOutput.getStickyNoteId());
        createStickyNoteUseCase.execute(createStickyNoteInput, createStickyNoteOutput);
        generatedWidgetIds.add(createStickyNoteOutput.getStickyNoteId());

        return generatedWidgetIds;
    }
}
