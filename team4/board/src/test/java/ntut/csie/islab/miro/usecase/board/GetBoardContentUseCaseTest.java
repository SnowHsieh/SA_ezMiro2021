package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.presenter.GetBoardContentPresenter;
import ntut.csie.islab.miro.adapter.presenter.getContent.BoardContentViewModel;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.figure.adapter.repository.figure.FigureRepository;
import ntut.csie.islab.miro.figure.entity.model.figure.Figure;
import ntut.csie.islab.miro.figure.entity.model.figure.Position;
import ntut.csie.islab.miro.figure.entity.model.figure.ShapeKindEnum;
import ntut.csie.islab.miro.figure.entity.model.figure.Style;
import ntut.csie.islab.miro.figure.usecase.figure.FigureDto;
import ntut.csie.islab.miro.usecase.board.CreateBoardUseCase;
import ntut.csie.islab.miro.usecase.board.CreateBoardUseCaseInput;
import ntut.csie.islab.miro.usecase.stickyNote.CreateStickyNoteInput;
import ntut.csie.islab.miro.usecase.stickyNote.CreateStickyNoteUseCase;
import ntut.csie.islab.miro.usecase.stickyNote.EditStickyNoteInput;
import ntut.csie.islab.miro.usecase.stickyNote.EditStickyNoteUseCase;
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
    public FigureRepository figureRepository;

    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        boardRepository = new BoardRepository();
        figureRepository = new FigureRepository();
    }
    @Test
    public void test_get_board_with_empty_content_with_exist_board_id(){
        GetBoardContentUseCase getBoardContentUseCase= new GetBoardContentUseCase(domainEventBus,boardRepository,figureRepository);
        GetBoardContentUseCaseInput input =  getBoardContentUseCase.newInput();
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
    public void test_get_board_with_nonempty_content_with_exist_board_id(){
        //Create a Board
        CreateBoardUseCase createBoardUseCase= new CreateBoardUseCase(domainEventBus,boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput =  createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardUseCaseoutput = CqrsCommandPresenter.newInstance();
        createBoardUseCaseInput.setTeamId(UUID.randomUUID());
        createBoardUseCaseInput.setBoardName("EventStorming");
        createBoardUseCase.execute(createBoardUseCaseInput,createBoardUseCaseoutput);
        assertNotNull(createBoardUseCaseoutput.getId());
        assertEquals(ExitCode.SUCCESS,createBoardUseCaseoutput.getExitCode());

        UUID boardId = UUID.fromString(createBoardUseCaseoutput.getId());

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
                new Position(20,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, "#f9f900"));
        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS,domainEventStickyNote.getExitCode());

        assertEquals(boardId,figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFigureSize());
        assertEquals("#f9f900", figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

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
                new Position(10,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, "#f9f900"));
        assertEquals(boardId,figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getBoardId());
        assertEquals(10, figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getPosition().getY());
        assertEquals("", figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getContent());
        assertEquals(12, figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getFigureSize());
        assertEquals("#f9f900", figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getColor());

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
                new Position(0,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, "#f9f900"));

        assertEquals(boardId,figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getBoardId());
        assertEquals(0, figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getPosition().getY());
        assertEquals("", figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getContent());
        assertEquals(12, figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getFigureSize());
        assertEquals("#f9f900", figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getColor());

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
                new Position(15,0),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, "#f9f900"));

        assertEquals(boardId,figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getBoardId());
        assertEquals(15, figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getX());
        assertEquals(0, figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getY());
        assertEquals("", figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getContent());
        assertEquals(12, figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getFigureSize());
        assertEquals("#f9f900", figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 4
        GetBoardContentPresenter fourFiguresInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, fourFiguresInBoardPresenter.getBoardId());
        assertEquals(4, fourFiguresInBoardPresenter.getFigures().size());
        BoardContentViewModel fourFiguresInBoardViewModel = fourFiguresInBoardPresenter.buildViewModel();
        assertEquals(boardId, fourFiguresInBoardViewModel.getBoardId());
        assertEquals(4, fourFiguresInBoardViewModel.getFigureDtos().size());

    }

    @Test
    public void test_get_board_content_with_edited_figure_and_exist_board_id(){
        //Create a Board
        CqrsCommandPresenter createBoardUseCaseOutput = generateCreateBoardUseCaseoutput(UUID.randomUUID(),"EventStorming");
        assertNotNull(createBoardUseCaseOutput.getId());
        assertEquals(ExitCode.SUCCESS,createBoardUseCaseOutput.getExitCode());

        UUID boardId = UUID.fromString(createBoardUseCaseOutput.getId());

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
                new Position(20,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, "#f9f900"));
        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS,domainEventStickyNote.getExitCode());

        assertEquals(boardId,figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFigureSize());
        assertEquals("#f9f900", figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

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
                new  Style(12, ShapeKindEnum.RECTANGLE, 100, "#f28500")
        );

        assertEquals("sticky\n note \n created", figureRepository.findById(boardId,UUID.fromString(editDomainEventStickyNoteOutput.getId())).get().getContent());
        assertEquals("#f28500", figureRepository.findById(boardId,UUID.fromString(editDomainEventStickyNoteOutput.getId())).get().getStyle().getColor());

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
    public void test_create_four_stickyNotes_and_edit_them_in_the_same_board(){
        //Create a Board
        CqrsCommandPresenter createBoardUseCaseOutput = generateCreateBoardUseCaseoutput(UUID.randomUUID(),"EventStorming");
        assertNotNull(createBoardUseCaseOutput.getId());
        assertEquals(ExitCode.SUCCESS,createBoardUseCaseOutput.getExitCode());

        UUID boardId = UUID.fromString(createBoardUseCaseOutput.getId());

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
                new Position(20,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, "#f9f900"));
        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS,domainEventStickyNote.getExitCode());

        assertEquals(boardId,figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFigureSize());
        assertEquals("#f9f900", figureRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

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
                new Position(10,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, "#f9f900"));
        assertEquals(boardId,figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getBoardId());
        assertEquals(10, figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getPosition().getY());
        assertEquals("", figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getContent());
        assertEquals(12, figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getFigureSize());
        assertEquals("#f9f900", figureRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getColor());

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
                new Position(0,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, "#f9f900"));

        assertEquals(boardId,figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getBoardId());
        assertEquals(0, figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getPosition().getY());
        assertEquals("", figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getContent());
        assertEquals(12, figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getFigureSize());
        assertEquals("#f9f900", figureRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getColor());

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
                new Position(15,0),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100, "#f9f900"));

        assertEquals(boardId,figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getBoardId());
        assertEquals(15, figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getX());
        assertEquals(0, figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getY());
        assertEquals("", figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getContent());
        assertEquals(12, figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getFigureSize());
        assertEquals("#f9f900", figureRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getColor());

        //Check BoardContent = 4
        GetBoardContentPresenter fourFiguresInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, fourFiguresInBoardPresenter.getBoardId());
        assertEquals(4, fourFiguresInBoardPresenter.getFigures().size());
        BoardContentViewModel fourFiguresInBoardViewModel = fourFiguresInBoardPresenter.buildViewModel();
        assertEquals(boardId, fourFiguresInBoardViewModel.getBoardId());
        assertEquals(4, fourFiguresInBoardViewModel.getFigureDtos().size());

        // edit DomainEventStickyNote
        CqrsCommandPresenter editDomainEventStickyNoteOutput = editStickyNoteUseCaseOutput(
                boardId,
                domainEventStickyNote.getId(),
                "sticky\n note \n created",
                new  Style(12, ShapeKindEnum.RECTANGLE, 100, "#f28500")
        );

        assertEquals("sticky\n note \n created", figureRepository.findById(boardId,UUID.fromString(editDomainEventStickyNoteOutput.getId())).get().getContent());
        assertEquals("#f28500", figureRepository.findById(boardId,UUID.fromString(editDomainEventStickyNoteOutput.getId())).get().getStyle().getColor());

        //Check BoardContent = 4
        GetBoardContentPresenter oneFigureEditedInBoardPresenter = generateGetBoardContentUseCaseOutput(boardId);
        assertEquals(boardId, oneFigureEditedInBoardPresenter.getBoardId());
        assertEquals(4, oneFigureEditedInBoardPresenter.getFigures().size());
        BoardContentViewModel oneFigureEditedInBoardViewModel = oneFigureEditedInBoardPresenter.buildViewModel();
        assertEquals(boardId, oneFigureEditedInBoardViewModel.getBoardId());
        assertEquals(4, oneFigureEditedInBoardViewModel.getFigureDtos().size());

        //Check is EditEvent(Content and Color had been changed) Success
        FigureDto domainEventStickyNoteDto = oneFigureEditedInBoardViewModel.getFigureDtos().stream().filter(s->s.getFigureId().equals(UUID.fromString(domainEventStickyNote.getId()))).findFirst().get();

        assertEquals(boardId, domainEventStickyNoteDto.getBoardId());
        assertEquals("sticky\n note \n created", domainEventStickyNoteDto.getContent());
        assertEquals("#f28500", domainEventStickyNoteDto.getStyle().getColor());






    }

//    private  FigureDto getSpecifiedFigureDto(,FigureDto StickyNoteDto){
//
//        return oneFigureEditedInBoardViewModel.getFigureDtos().stream().filter(s->s.getFigureId().equals(UUID.fromString(domainEventStickyNote.getId()))).findFirst().get();
//
//    }


    private CqrsCommandPresenter generateCreateBoardUseCaseoutput(UUID id,String boardName){
        CreateBoardUseCase createBoardUseCase= new CreateBoardUseCase(domainEventBus,boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput =  createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardUseCaseoutput = CqrsCommandPresenter.newInstance();
        createBoardUseCaseInput.setTeamId(id);
        createBoardUseCaseInput.setBoardName(boardName);
        createBoardUseCase.execute(createBoardUseCaseInput,createBoardUseCaseoutput);

        return createBoardUseCaseoutput;
    }

    private CqrsCommandPresenter generateCreateStickyNoteUseCaseOutput(UUID id, Position position,String content,Style style){
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(figureRepository, domainEventBus);
        CreateStickyNoteInput input = createStickyNoteUseCase.newInput();
        input.setBoardId(id);
        input.setPosition(position.getX(),position.getY());
        input.setContent(content);
        input.setStyle(style);

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        createStickyNoteUseCase.execute(input, output);

        return output;
    }

    private CqrsCommandPresenter editStickyNoteUseCaseOutput(UUID boardId,String figureId, String content,Style style){
        EditStickyNoteUseCase editStickyNoteUseCase = new EditStickyNoteUseCase(figureRepository, domainEventBus);
        EditStickyNoteInput editStickyNoteUseCaseInput = editStickyNoteUseCase.newInput();
        CqrsCommandPresenter editStickyNoteUseCaseOutput = CqrsCommandPresenter.newInstance();

        editStickyNoteUseCaseInput.setBoardId (boardId);
        editStickyNoteUseCaseInput.setFigureId(UUID.fromString(figureId));
        editStickyNoteUseCaseInput.setContent(content);
        editStickyNoteUseCaseInput.setStyle(style);
        editStickyNoteUseCase.execute(editStickyNoteUseCaseInput, editStickyNoteUseCaseOutput);

        return editStickyNoteUseCaseOutput;
    }

    private GetBoardContentPresenter generateGetBoardContentUseCaseOutput(UUID boardId){

        GetBoardContentUseCase getBoardContentUseCase= new GetBoardContentUseCase(domainEventBus,boardRepository,figureRepository);
        GetBoardContentUseCaseInput input =  getBoardContentUseCase.newInput();
        input.setBoardId(boardId);
        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        getBoardContentUseCase.execute(input, presenter);

        return presenter;
    }



}
