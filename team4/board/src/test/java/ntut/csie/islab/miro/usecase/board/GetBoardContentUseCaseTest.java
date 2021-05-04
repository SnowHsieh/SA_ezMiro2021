package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.islab.miro.adapter.presenter.GetBoardContentPresenter;
import ntut.csie.islab.miro.adapter.presenter.getContent.BoardContentViewModel;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.textFigure.Position;
import ntut.csie.islab.miro.entity.model.textFigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.textFigure.Style;
import ntut.csie.islab.miro.usecase.eventHandler.NotifyBoard;
import ntut.csie.islab.miro.usecase.textFigure.TextFigureDto;
import ntut.csie.islab.miro.usecase.textFigure.stickyNote.*;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetBoardContentUseCaseTest {
    public DomainEventBus domainEventBus;
    public BoardRepository boardRepository;
    public TextFigureRepository textFigureRepository;
    public NotifyBoardAdapter notifyBoardAdapter;

    @BeforeEach
    public void setUp() {
        domainEventBus = new GoogleEventBus();
        boardRepository = new BoardRepository();
        textFigureRepository = new TextFigureRepository();
        notifyBoardAdapter = new NotifyBoardAdapter(new NotifyBoard(boardRepository, domainEventBus));
        domainEventBus.register(notifyBoardAdapter);
    }

    @Test
    public void test_get_board_with_empty_content_with_exist_board_id() {
        GetBoardContentUseCase getBoardContentUseCase = new GetBoardContentUseCase(domainEventBus, boardRepository, textFigureRepository);
        GetBoardContentInput input = getBoardContentUseCase.newInput();
        UUID boardId = UUID.randomUUID();
        input.setBoardId(boardId);
        GetBoardContentPresenter output = new GetBoardContentPresenter();

        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        getBoardContentUseCase.execute(input, presenter);

        assertEquals(boardId, presenter.getBoardId());
        assertEquals(0, presenter.getFigures().size());

        BoardContentViewModel boardContentViewModel = presenter.buildViewModel();

        assertEquals(boardId, boardContentViewModel.getBoardId());
        assertEquals(0, boardContentViewModel.getFigureDtos().size());

    }

    @Test
    public void test_get_board_with_nonempty_content_with_exist_board_id() {
        //Create a Board
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(domainEventBus, boardRepository);
        CreateBoardInput createBoardInput = createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardUseCaseoutput = CqrsCommandPresenter.newInstance();
        createBoardInput.setTeamId(UUID.randomUUID());
        createBoardInput.setBoardName("EventStorming");
        createBoardUseCase.execute(createBoardInput, createBoardUseCaseoutput);
        assertNotNull(createBoardUseCaseoutput.getId());
        assertEquals(ExitCode.SUCCESS, createBoardUseCaseoutput.getExitCode());


        UUID boardId = UUID.fromString(createBoardUseCaseoutput.getId());
        Board board = this.boardRepository.findById(boardId).get();
        //Check BoardContent = 0
        GetBoardContentPresenter emptyBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, emptyBoardPresenter.getBoardId());
        assertEquals(0, emptyBoardPresenter.getFigures().size());
        BoardContentViewModel emptyBoardViewModel = emptyBoardPresenter.buildViewModel();
        assertEquals(boardId, emptyBoardViewModel.getBoardId());
        assertEquals(0, emptyBoardViewModel.getFigureDtos().size());

        //Create a domainEventStickyNote
        CqrsCommandPresenter domainEventStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(20, 10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));

        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS, domainEventStickyNote.getExitCode());

        assertEquals(boardId, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getWidth());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getHeight());
        assertEquals("#f9f900", textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 1
        GetBoardContentPresenter oneFigureInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, oneFigureInBoardPresenter.getBoardId());
        assertEquals(1, oneFigureInBoardPresenter.getFigures().size());
        BoardContentViewModel oneFigureInBoardViewModel = oneFigureInBoardPresenter.buildViewModel();
        assertEquals(boardId, oneFigureInBoardViewModel.getBoardId());
        assertEquals(1, oneFigureInBoardViewModel.getFigureDtos().size());


        //Create a commandStickyNote in SameBoard
        CqrsCommandPresenter commandStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(10, 10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));
        assertEquals(boardId, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getBoardId());
        assertEquals(10, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getPosition().getY());
        assertEquals("", textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getContent());
        assertEquals(12, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getStyle().getWidth());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getHeight());
        assertEquals("#f9f900", textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 2
        GetBoardContentPresenter twoFiguresInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, twoFiguresInBoardPresenter.getBoardId());
        assertEquals(2, twoFiguresInBoardPresenter.getFigures().size());
        BoardContentViewModel twoFiguresInBoardViewModel = twoFiguresInBoardPresenter.buildViewModel();
        assertEquals(boardId, twoFiguresInBoardViewModel.getBoardId());
        assertEquals(2, twoFiguresInBoardViewModel.getFigureDtos().size());


        //Create a readModelStickyNote in same board
        CqrsCommandPresenter readModelStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(0, 10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));
        assertEquals(boardId, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getBoardId());
        assertEquals(0, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getPosition().getY());
        assertEquals("", textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getContent());
        assertEquals(12, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getStyle().getWidth());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getHeight());
        assertEquals("#f9f900", textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 3
        GetBoardContentPresenter threeFiguresInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, threeFiguresInBoardPresenter.getBoardId());
        assertEquals(3, threeFiguresInBoardPresenter.getFigures().size());
        BoardContentViewModel threeFiguresInBoardViewModel = threeFiguresInBoardPresenter.buildViewModel();
        assertEquals(boardId, threeFiguresInBoardViewModel.getBoardId());
        assertEquals(3, threeFiguresInBoardViewModel.getFigureDtos().size());

        //Create a aggregateStickyNote in same board
        CqrsCommandPresenter aggregateStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(15, 0),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));
        assertEquals(boardId, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getBoardId());
        assertEquals(15, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getX());
        assertEquals(0, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getY());
        assertEquals("", textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getContent());
        assertEquals(12, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getWidth());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getHeight());
        assertEquals("#f9f900", textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 4
        GetBoardContentPresenter fourFiguresInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, fourFiguresInBoardPresenter.getBoardId());
        assertEquals(4, fourFiguresInBoardPresenter.getFigures().size());
        BoardContentViewModel fourFiguresInBoardViewModel = fourFiguresInBoardPresenter.buildViewModel();
        assertEquals(boardId, fourFiguresInBoardViewModel.getBoardId());
        assertEquals(4, fourFiguresInBoardViewModel.getFigureDtos().size());

    }

    @Test
    public void test_get_board_content_with_edited_figure_and_exist_board_id() {
        //Create a Board
        CqrsCommandPresenter createBoardUseCaseOutput = generateCreateBoardUseCaseoutput(UUID.randomUUID(), "EventStorming");
        assertNotNull(createBoardUseCaseOutput.getId());
        assertEquals(ExitCode.SUCCESS, createBoardUseCaseOutput.getExitCode());

        UUID boardId = UUID.fromString(createBoardUseCaseOutput.getId());
        Board board = this.boardRepository.findById(boardId).get();

        //Check BoardContent = 0
        GetBoardContentPresenter emptyBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, emptyBoardPresenter.getBoardId());
        assertEquals(0, emptyBoardPresenter.getFigures().size());
        BoardContentViewModel emptyBoardViewModel = emptyBoardPresenter.buildViewModel();
        assertEquals(boardId, emptyBoardViewModel.getBoardId());
        assertEquals(0, emptyBoardViewModel.getFigureDtos().size());

        //Create a domainEventStickyNote
        CqrsCommandPresenter domainEventStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(20, 10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));

        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS, domainEventStickyNote.getExitCode());

        assertEquals(boardId, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getWidth());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getHeight());
        assertEquals("#f9f900", textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 1
        GetBoardContentPresenter oneFigureInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, oneFigureInBoardPresenter.getBoardId());
        assertEquals(1, oneFigureInBoardPresenter.getFigures().size());
        BoardContentViewModel oneFigureInBoardViewModel = oneFigureInBoardPresenter.buildViewModel();
        assertEquals(boardId, oneFigureInBoardViewModel.getBoardId());
        assertEquals(1, oneFigureInBoardViewModel.getFigureDtos().size());


        CqrsCommandPresenter editDomainEventStickyNoteOutput = editStickyNoteUseCaseOutput(
                boardId,
                domainEventStickyNote.getId(),
                "sticky\n note \n created",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f28500")
        );

        assertEquals("sticky\n note \n created", textFigureRepository.findById(boardId, UUID.fromString(editDomainEventStickyNoteOutput.getId())).get().getContent());
        assertEquals("#f28500", textFigureRepository.findById(boardId, UUID.fromString(editDomainEventStickyNoteOutput.getId())).get().getStyle().getColor());

        //Check BoardContent = 1
        GetBoardContentPresenter oneFigureEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, oneFigureEditedInBoardPresenter.getBoardId());
        assertEquals(1, oneFigureEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel oneFigureEditedInBoardViewModel = oneFigureEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, oneFigureEditedInBoardViewModel.getBoardId());
        assertEquals(1, oneFigureEditedInBoardViewModel.getFigureDtos().size());

        //Check is EditEvent(Content and Color had been changed) Success
        assertEquals(boardId, oneFigureEditedInBoardViewModel.getFigureDtos().get(0).getBoardId());
        assertEquals("sticky\n note \n created", oneFigureEditedInBoardViewModel.getFigureDtos().get(0).getContent());
        assertEquals("#f28500", oneFigureEditedInBoardViewModel.getFigureDtos().get(0).getStyle().getColor());


    }

    @Test
    public void test_create_four_stickyNotes_and_edit_them_in_the_same_board() {
        //Create a Board
        CqrsCommandPresenter createBoardUseCaseOutput = generateCreateBoardUseCaseoutput(UUID.randomUUID(), "EventStorming");
        assertNotNull(createBoardUseCaseOutput.getId());
        assertEquals(ExitCode.SUCCESS, createBoardUseCaseOutput.getExitCode());

        UUID boardId = UUID.fromString(createBoardUseCaseOutput.getId());
        Board board = this.boardRepository.findById(boardId).get();

        //Check BoardContent = 0
        GetBoardContentPresenter emptyBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, emptyBoardPresenter.getBoardId());
        assertEquals(0, emptyBoardPresenter.getFigures().size());
        BoardContentViewModel emptyBoardViewModel = emptyBoardPresenter.buildViewModel();
        assertEquals(boardId, emptyBoardViewModel.getBoardId());
        assertEquals(0, emptyBoardViewModel.getFigureDtos().size());

        //Create a domainEventStickyNote
        CqrsCommandPresenter domainEventStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(20, 10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));

        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS, domainEventStickyNote.getExitCode());

        assertEquals(boardId, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getWidth());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getHeight());
        assertEquals("#f9f900", textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 1
        GetBoardContentPresenter oneFigureInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, oneFigureInBoardPresenter.getBoardId());
        assertEquals(1, oneFigureInBoardPresenter.getFigures().size());
        BoardContentViewModel oneFigureInBoardViewModel = oneFigureInBoardPresenter.buildViewModel();
        assertEquals(boardId, oneFigureInBoardViewModel.getBoardId());
        assertEquals(1, oneFigureInBoardViewModel.getFigureDtos().size());

        //Create a commandStickyNote in SameBoard
        CqrsCommandPresenter commandStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(10, 10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));
        assertEquals(boardId, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getBoardId());
        assertEquals(10, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getPosition().getY());
        assertEquals("", textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getContent());
        assertEquals(12, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getStyle().getWidth());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getHeight());
        assertEquals("#f9f900", textFigureRepository.findById(boardId, UUID.fromString(commandStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 2
        GetBoardContentPresenter twoFiguresInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, twoFiguresInBoardPresenter.getBoardId());
        assertEquals(2, twoFiguresInBoardPresenter.getFigures().size());
        BoardContentViewModel twoFiguresInBoardViewModel = twoFiguresInBoardPresenter.buildViewModel();
        assertEquals(boardId, twoFiguresInBoardViewModel.getBoardId());
        assertEquals(2, twoFiguresInBoardViewModel.getFigureDtos().size());


        //Create a readModelStickyNote in same board
        CqrsCommandPresenter readModelStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(0, 10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));
        assertEquals(boardId, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getBoardId());
        assertEquals(0, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getPosition().getY());
        assertEquals("", textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getContent());
        assertEquals(12, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getStyle().getWidth());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getHeight());
        assertEquals("#f9f900", textFigureRepository.findById(boardId, UUID.fromString(readModelStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 3
        GetBoardContentPresenter threeFiguresInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, threeFiguresInBoardPresenter.getBoardId());
        assertEquals(3, threeFiguresInBoardPresenter.getFigures().size());
        BoardContentViewModel threeFiguresInBoardViewModel = threeFiguresInBoardPresenter.buildViewModel();
        assertEquals(boardId, threeFiguresInBoardViewModel.getBoardId());
        assertEquals(3, threeFiguresInBoardViewModel.getFigureDtos().size());

        //Create a aggregateStickyNote in same board
        CqrsCommandPresenter aggregateStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(15, 0),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900"));
        assertEquals(boardId, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getBoardId());
        assertEquals(15, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getX());
        assertEquals(0, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getY());
        assertEquals("", textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getContent());
        assertEquals(12, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getWidth());
        assertEquals(100, textFigureRepository.findById(boardId, UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getHeight());
        assertEquals("#f9f900", textFigureRepository.findById(boardId, UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 4
        GetBoardContentPresenter fourFiguresInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, fourFiguresInBoardPresenter.getBoardId());
        assertEquals(4, fourFiguresInBoardPresenter.getFigures().size());
        BoardContentViewModel fourFiguresInBoardViewModel = fourFiguresInBoardPresenter.buildViewModel();
        assertEquals(boardId, fourFiguresInBoardViewModel.getBoardId());
        assertEquals(4, fourFiguresInBoardViewModel.getFigureDtos().size());

        // getcontent DomainEventStickyNote
        CqrsCommandPresenter editDomainEventStickyNoteOutput = editStickyNoteUseCaseOutput(
                boardId,
                domainEventStickyNote.getId(),
                "sticky\n note \n created",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f28500")
        );

        assertEquals("sticky\n note \n created", textFigureRepository.findById(boardId, UUID.fromString(editDomainEventStickyNoteOutput.getId())).get().getContent());
        assertEquals("#f28500", textFigureRepository.findById(boardId, UUID.fromString(editDomainEventStickyNoteOutput.getId())).get().getStyle().getColor());

        //Check BoardContent = 4
        GetBoardContentPresenter oneFigureEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, oneFigureEditedInBoardPresenter.getBoardId());
        assertEquals(4, oneFigureEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel oneFigureEditedInBoardViewModel = oneFigureEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, oneFigureEditedInBoardViewModel.getBoardId());
        assertEquals(4, oneFigureEditedInBoardViewModel.getFigureDtos().size());

        //Check domainEventStickyNote is EditEvent(Content and Color had been changed) Success
        TextFigureDto domainEventStickyNoteDto = getSpecifiedFigureDto(oneFigureEditedInBoardViewModel, domainEventStickyNote.getId());
        assertEquals(boardId, domainEventStickyNoteDto.getBoardId());
        assertEquals("sticky\n note \n created", domainEventStickyNoteDto.getContent());
        assertEquals("#f28500", domainEventStickyNoteDto.getStyle().getColor());

        //getcontent CommandStickyNote
        CqrsCommandPresenter editCommandStickyNoteOutput = editStickyNoteUseCaseOutput(
                boardId,
                commandStickyNote.getId(),
                "create sticky note",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#0080ff")
        );

        assertEquals("create sticky note", textFigureRepository.findById(boardId, UUID.fromString(editCommandStickyNoteOutput.getId())).get().getContent());
        assertEquals("#0080ff", textFigureRepository.findById(boardId, UUID.fromString(editCommandStickyNoteOutput.getId())).get().getStyle().getColor());

        //Check BoardContent = 4
        GetBoardContentPresenter commandStickyNoteEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, commandStickyNoteEditedInBoardPresenter.getBoardId());
        assertEquals(4, commandStickyNoteEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel commandStickyNoteEditedInBoardViewModel = commandStickyNoteEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, commandStickyNoteEditedInBoardViewModel.getBoardId());
        assertEquals(4, commandStickyNoteEditedInBoardViewModel.getFigureDtos().size());

        //Check CommandStickyNoteDto is EditEvent(Content and Color had been changed) Success
        TextFigureDto commandStickyNoteDto = getSpecifiedFigureDto(
                commandStickyNoteEditedInBoardViewModel,
                commandStickyNote.getId());
        assertEquals(boardId, commandStickyNoteDto.getBoardId());
        assertEquals("create sticky note", commandStickyNoteDto.getContent());
        assertEquals("#0080ff", commandStickyNoteDto.getStyle().getColor());

        //getcontent ReadModelStickyNote
        CqrsCommandPresenter editReadModelStickyNoteOutput = editStickyNoteUseCaseOutput(
                boardId,
                readModelStickyNote.getId(),
                "stickNoteId,\n" +
                        "\n" +
                        "content,\n" +
                        "\n" +
                        "style(font,shape,size,color)",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#28ff28")
        );

        assertEquals("stickNoteId,\n" +
                "\n" +
                "content,\n" +
                "\n" +
                "style(font,shape,size,color)", textFigureRepository.findById(boardId, UUID.fromString(editReadModelStickyNoteOutput.getId())).get().getContent());
        assertEquals("#28ff28", textFigureRepository.findById(boardId, UUID.fromString(editReadModelStickyNoteOutput.getId())).get().getStyle().getColor());


        //Check BoardContent = 4
        GetBoardContentPresenter readModelStickyNoteEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, readModelStickyNoteEditedInBoardPresenter.getBoardId());
        assertEquals(4, readModelStickyNoteEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel readModelStickyNoteEditedInBoardViewModel = readModelStickyNoteEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, readModelStickyNoteEditedInBoardViewModel.getBoardId());
        assertEquals(4, readModelStickyNoteEditedInBoardViewModel.getFigureDtos().size());

        //Check readModelStickyNoteDto is EditEvent(Content and Color had been changed) Success
        TextFigureDto readModelStickyNoteDto = getSpecifiedFigureDto(
                readModelStickyNoteEditedInBoardViewModel,
                readModelStickyNote.getId());
        assertEquals(boardId, readModelStickyNoteDto.getBoardId());
        assertEquals("stickNoteId,\n" +
                "\n" +
                "content,\n" +
                "\n" +
                "style(font,shape,size,color)", readModelStickyNoteDto.getContent());
        assertEquals("#28ff28", readModelStickyNoteDto.getStyle().getColor());


        CqrsCommandPresenter editAggregateStickyNoteOutput = editStickyNoteUseCaseOutput(
                boardId,
                aggregateStickyNote.getId(),
                "sticky note",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, 100, "#f9f900")
        );
        assertEquals("sticky note", textFigureRepository.findById(boardId, UUID.fromString(editAggregateStickyNoteOutput.getId())).get().getContent());
        assertEquals("#f9f900", textFigureRepository.findById(boardId, UUID.fromString(editAggregateStickyNoteOutput.getId())).get().getStyle().getColor());


        //Check BoardContent = 4
        GetBoardContentPresenter aggregateStickyNoteEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, aggregateStickyNoteEditedInBoardPresenter.getBoardId());
        assertEquals(4, aggregateStickyNoteEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel aggregateStickyNoteEditedInBoardViewModel = aggregateStickyNoteEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, aggregateStickyNoteEditedInBoardViewModel.getBoardId());
        assertEquals(4, aggregateStickyNoteEditedInBoardViewModel.getFigureDtos().size());

        //Check aggregateStickyNoteDto is EditEvent(Content and Color had been changed) Success
        TextFigureDto aggregateStickyNoteDto = getSpecifiedFigureDto(
                aggregateStickyNoteEditedInBoardViewModel,
                aggregateStickyNote.getId());
        assertEquals(boardId, aggregateStickyNoteDto.getBoardId());
        assertEquals("sticky note", aggregateStickyNoteDto.getContent());
        assertEquals("#f9f900", aggregateStickyNoteDto.getStyle().getColor());

    }


    private TextFigureDto getSpecifiedFigureDto(BoardContentViewModel boardContentViewModel, String id) {
        return boardContentViewModel.getFigureDtos().stream().filter(s -> s.getFigureId().equals(UUID.fromString(id))).findFirst().get();
    }


    private CqrsCommandPresenter generateCreateBoardUseCaseoutput(UUID id, String boardName) {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(domainEventBus, boardRepository);
        CreateBoardInput createBoardInput = createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardUseCaseoutput = CqrsCommandPresenter.newInstance();
        createBoardInput.setTeamId(id);
        createBoardInput.setBoardName(boardName);
        createBoardUseCase.execute(createBoardInput, createBoardUseCaseoutput);

        return createBoardUseCaseoutput;
    }

    private CqrsCommandPresenter generateCreateStickyNoteUseCaseOutput(UUID id, Position position, String content, Style style) {
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(textFigureRepository, domainEventBus);
        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();
        input.setBoardId(id);
        input.setPosition(position.getX(), position.getY());
        input.setContent(content);
        input.setStyle(style);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        createStickyNoteUseCase.execute(input, output);

        return output;
    }

    private CqrsCommandPresenter editStickyNoteUseCaseOutput(UUID boardId, String figureId, String content, Style style) {
        EditStickyNoteUseCase editStickyNoteUseCase = new EditStickyNoteUseCase(textFigureRepository, domainEventBus);
        EditStickyNoteInput editStickyNoteUseCaseInput = editStickyNoteUseCase.newInput();
        CqrsCommandPresenter editStickyNoteUseCaseOutput = CqrsCommandPresenter.newInstance();

        editStickyNoteUseCaseInput.setBoardId(boardId);
        editStickyNoteUseCaseInput.setFigureId(UUID.fromString(figureId));
        editStickyNoteUseCaseInput.setContent(content);
        editStickyNoteUseCaseInput.setStyle(style);
        editStickyNoteUseCase.execute(editStickyNoteUseCaseInput, editStickyNoteUseCaseOutput);

        return editStickyNoteUseCaseOutput;
    }

    private GetBoardContentPresenter generateGetBoardContentUseCaseOutput(UUID boardId) {

        GetBoardContentUseCase getBoardContentUseCase = new GetBoardContentUseCase(domainEventBus, boardRepository, textFigureRepository);
        GetBoardContentInput input = getBoardContentUseCase.newInput();
        input.setBoardId(boardId);
        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        getBoardContentUseCase.execute(input, presenter);

        return presenter;
    }


}
