package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.presenter.getcontent.GetBoardContentPresenter;
import ntut.csie.islab.miro.adapter.presenter.getcontent.BoardContentViewModel;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.Style;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.board.createboard.CreateBoardInput;
import ntut.csie.islab.miro.usecase.board.createboard.CreateBoardUseCase;
import ntut.csie.islab.miro.usecase.board.getboardcontent.GetBoardContentInput;
import ntut.csie.islab.miro.usecase.board.getboardcontent.GetBoardContentUseCase;
import ntut.csie.islab.miro.usecase.figure.FigureDto;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetBoardContentUseCaseTest extends AbstractSpringBootJpaTest {


    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        domainEventBus.register(notifyBoardAdapter);

    }

    @Test
    public void test_get_board_with_empty_content_with_exist_board_id() {


        GetBoardContentUseCase getBoardContentUseCase = new GetBoardContentUseCase(domainEventBus, boardRepository, stickyNoteRepository, lineRepository);
        GetBoardContentInput input = getBoardContentUseCase.newInput();
        input.setBoardId(board.getBoardId());
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
        //Create a New Board by AbstractSpringBootJpaTest

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
                "", 100, 100,
                new Style(12, ShapeKindEnum.RECTANGLE, "#f9f900"));

        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS, domainEventStickyNote.getExitCode());

        assertEquals(boardId, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getWidth());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getHeight());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

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
                100,
                100,
                new Style(12, ShapeKindEnum.RECTANGLE, "#f9f900"));
        assertEquals(boardId, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getBoardId());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getWidth());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getHeight());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getStyle().getColor());

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
                "", 100, 100,
                new Style(12, ShapeKindEnum.RECTANGLE, "#f9f900"));
        assertEquals(boardId, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getBoardId());
        assertEquals(0, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getWidth());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getHeight());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getStyle().getColor());

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
                "", 100, 100,
                new Style(12, ShapeKindEnum.RECTANGLE, "#f9f900"));
        assertEquals(boardId, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getBoardId());
        assertEquals(15, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getX());
        assertEquals(0, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getWidth());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getHeight());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getColor());

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
                "", 100, 100,
                new Style(12, ShapeKindEnum.RECTANGLE, "#f9f900"));

        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS, domainEventStickyNote.getExitCode());

        assertEquals(boardId, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getWidth());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getHeight());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 1
        GetBoardContentPresenter oneFigureInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, oneFigureInBoardPresenter.getBoardId());
        assertEquals(1, oneFigureInBoardPresenter.getFigures().size());
        BoardContentViewModel oneFigureInBoardViewModel = oneFigureInBoardPresenter.buildViewModel();
        assertEquals(boardId, oneFigureInBoardViewModel.getBoardId());
        assertEquals(1, oneFigureInBoardViewModel.getFigureDtos().size());


        //Check BoardContent = 1
        GetBoardContentPresenter oneFigureEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, oneFigureEditedInBoardPresenter.getBoardId());
        assertEquals(1, oneFigureEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel oneFigureEditedInBoardViewModel = oneFigureEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, oneFigureEditedInBoardViewModel.getBoardId());
        assertEquals(1, oneFigureEditedInBoardViewModel.getFigureDtos().size());

        //Check is EditEvent(Content and Color had been changed) Success
//        assertEquals(boardId, oneFigureEditedInBoardViewModel.getFigureDtos().get(0).getBoardId());
//        assertEquals("sticky\n note \n created", oneFigureEditedInBoardViewModel.getFigureDtos().get(0).getContent());
//        assertEquals("#f28500", oneFigureEditedInBoardViewModel.getFigureDtos().get(0).getStyle().getColor());


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
                "", 100, 100,
                new Style(12, ShapeKindEnum.RECTANGLE, "#f9f900"));

        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS, domainEventStickyNote.getExitCode());

        assertEquals(boardId, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getWidth());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getHeight());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

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
                "", 100, 100,
                new Style(12, ShapeKindEnum.RECTANGLE, "#f9f900"));
        assertEquals(boardId, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getBoardId());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getWidth());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getHeight());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getStyle().getColor());

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
                "", 100, 100,
                new Style(12, ShapeKindEnum.RECTANGLE, "#f9f900"));
        assertEquals(boardId, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getBoardId());
        assertEquals(0, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getWidth());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getHeight());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getStyle().getColor());

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
                "", 100, 100,
                new Style(12, ShapeKindEnum.RECTANGLE, "#f9f900"));
        assertEquals(boardId, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getBoardId());
        assertEquals(15, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getX());
        assertEquals(0, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getWidth());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getHeight());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 4
        GetBoardContentPresenter fourFiguresInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, fourFiguresInBoardPresenter.getBoardId());
        assertEquals(4, fourFiguresInBoardPresenter.getFigures().size());
        BoardContentViewModel fourFiguresInBoardViewModel = fourFiguresInBoardPresenter.buildViewModel();
        assertEquals(boardId, fourFiguresInBoardViewModel.getBoardId());
        assertEquals(4, fourFiguresInBoardViewModel.getFigureDtos().size());


        //Check BoardContent = 4
        GetBoardContentPresenter oneFigureEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, oneFigureEditedInBoardPresenter.getBoardId());
        assertEquals(4, oneFigureEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel oneFigureEditedInBoardViewModel = oneFigureEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, oneFigureEditedInBoardViewModel.getBoardId());
        assertEquals(4, oneFigureEditedInBoardViewModel.getFigureDtos().size());

        //Check domainEventStickyNote is EditEvent(Content and Color had been changed) Success
        FigureDto domainEventStickyNoteDto = getSpecifiedFigureDto(oneFigureEditedInBoardViewModel, domainEventStickyNote.getId());
        assertEquals(boardId, domainEventStickyNoteDto.getBoardId());

        //Check BoardContent = 4
        GetBoardContentPresenter commandStickyNoteEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, commandStickyNoteEditedInBoardPresenter.getBoardId());
        assertEquals(4, commandStickyNoteEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel commandStickyNoteEditedInBoardViewModel = commandStickyNoteEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, commandStickyNoteEditedInBoardViewModel.getBoardId());
        assertEquals(4, commandStickyNoteEditedInBoardViewModel.getFigureDtos().size());

        //Check CommandStickyNoteDto is EditEvent(Content and Color had been changed) Success
        FigureDto commandStickyNoteDto = getSpecifiedFigureDto(
                commandStickyNoteEditedInBoardViewModel,
                commandStickyNote.getId());
        assertEquals(boardId, commandStickyNoteDto.getBoardId());


        //Check BoardContent = 4
        GetBoardContentPresenter readModelStickyNoteEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, readModelStickyNoteEditedInBoardPresenter.getBoardId());
        assertEquals(4, readModelStickyNoteEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel readModelStickyNoteEditedInBoardViewModel = readModelStickyNoteEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, readModelStickyNoteEditedInBoardViewModel.getBoardId());
        assertEquals(4, readModelStickyNoteEditedInBoardViewModel.getFigureDtos().size());

        //Check readModelStickyNoteDto is EditEvent(Content and Color had been changed) Success
        FigureDto readModelStickyNoteDto = getSpecifiedFigureDto(
                readModelStickyNoteEditedInBoardViewModel,
                readModelStickyNote.getId());
        assertEquals(boardId, readModelStickyNoteDto.getBoardId());


        //Check BoardContent = 4
        GetBoardContentPresenter aggregateStickyNoteEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, aggregateStickyNoteEditedInBoardPresenter.getBoardId());
        assertEquals(4, aggregateStickyNoteEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel aggregateStickyNoteEditedInBoardViewModel = aggregateStickyNoteEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, aggregateStickyNoteEditedInBoardViewModel.getBoardId());
        assertEquals(4, aggregateStickyNoteEditedInBoardViewModel.getFigureDtos().size());

        //Check aggregateStickyNoteDto is EditEvent(Content and Color had been changed) Success
        FigureDto aggregateStickyNoteDto = getSpecifiedFigureDto(
                aggregateStickyNoteEditedInBoardViewModel,
                aggregateStickyNote.getId());
        assertEquals(boardId, aggregateStickyNoteDto.getBoardId());
//        assertEquals("sticky note", aggregateStickyNoteDto.getContent());
//        assertEquals("#f9f900", aggregateStickyNoteDto.getStyle().getColor());

    }

    @Test
    public void test_create_a_stickyNote_and_a_line_in_the_same_board() {
        //Create a New Board by AbstractSpringBootJpaTest

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
                "", 100, 100,
                new Style(12, ShapeKindEnum.RECTANGLE, "#f9f900"));

        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS, domainEventStickyNote.getExitCode());
        assertEquals(boardId, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getWidth());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getHeight());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 1
        GetBoardContentPresenter oneFigureInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, oneFigureInBoardPresenter.getBoardId());
        assertEquals(1, oneFigureInBoardPresenter.getFigures().size());
        BoardContentViewModel oneFigureInBoardViewModel = oneFigureInBoardPresenter.buildViewModel();
        assertEquals(boardId, oneFigureInBoardViewModel.getBoardId());
        assertEquals(1, oneFigureInBoardViewModel.getFigureDtos().size());


        //Create a Line in the board
        List<Position> positionList = new ArrayList<>();
        positionList.add(new Position(0, 50));
        positionList.add(new Position(100, 150));

        CqrsCommandPresenter newLine = generateCreateLineUseCaseOutput(
                boardId,
                positionList,
                5,
                "#000000"
        );


        //Check BoardContent = 2
        GetBoardContentPresenter twoFigureInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, twoFigureInBoardPresenter.getBoardId());
        assertEquals(2, twoFigureInBoardPresenter.getFigures().size());
        BoardContentViewModel twoFigureInBoardViewModel = twoFigureInBoardPresenter.buildViewModel();
        assertEquals(boardId, twoFigureInBoardViewModel.getBoardId());
        assertEquals(2, twoFigureInBoardViewModel.getFigureDtos().size());


    }

    private FigureDto getSpecifiedFigureDto(BoardContentViewModel boardContentViewModel, String id) {
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

    private GetBoardContentPresenter generateGetBoardContentUseCaseOutput(UUID boardId) {

        GetBoardContentUseCase getBoardContentUseCase = new GetBoardContentUseCase(domainEventBus, boardRepository, stickyNoteRepository, lineRepository);
        GetBoardContentInput input = getBoardContentUseCase.newInput();
        input.setBoardId(boardId);
        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        getBoardContentUseCase.execute(input, presenter);

        return presenter;
    }


}
