package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.presenter.GetBoardContentPresenter;
import ntut.csie.islab.miro.adapter.presenter.getContent.BoardContentViewModel;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.figure.adapter.repository.figure.FigureRepository;
import ntut.csie.islab.miro.figure.entity.model.figure.Position;
import ntut.csie.islab.miro.figure.entity.model.figure.ShapeKindEnum;
import ntut.csie.islab.miro.figure.entity.model.figure.Style;
import ntut.csie.islab.miro.usecase.board.CreateBoardUseCase;
import ntut.csie.islab.miro.usecase.board.CreateBoardUseCaseInput;
import ntut.csie.islab.miro.usecase.stickyNote.CreateStickyNoteInput;
import ntut.csie.islab.miro.usecase.stickyNote.CreateStickyNoteUseCase;
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

    @Test
    public void test_get_board_with_nonempty_content_with_exist_board_id(){

        //CreateBoard
        CreateBoardUseCase createBoardUseCase= new CreateBoardUseCase(domainEventBus,boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput =  createBoardUseCase.newInput();
        CqrsCommandPresenter createBoardUseCaseoutput = CqrsCommandPresenter.newInstance();
        createBoardUseCaseInput.setTeamId(UUID.randomUUID());
        createBoardUseCaseInput.setBoardName("EventStorming");

        createBoardUseCase.execute(createBoardUseCaseInput,createBoardUseCaseoutput);
        assertNotNull(createBoardUseCaseoutput.getId());
        assertEquals(ExitCode.SUCCESS,createBoardUseCaseoutput.getExitCode());

        UUID boardId = UUID.fromString(createBoardUseCaseoutput.getId());

        //Create 4 StickyNotes
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



        GetBoardContentUseCase getBoardContentUseCase= new GetBoardContentUseCase(domainEventBus,boardRepository,figureRepository);
        GetBoardContentUseCaseInput input =  getBoardContentUseCase.newInput();
        input.setBoardId(boardId);
        GetBoardContentPresenter output = new GetBoardContentPresenter();

        GetBoardContentPresenter presenter = new GetBoardContentPresenter();
        getBoardContentUseCase.execute(input, presenter);

        assertEquals(boardId, presenter.getBoardId());
        assertEquals(4, presenter.getFigures().size());

        BoardContentViewModel boardContentViewModel = presenter.buildViewModel();

        assertEquals(boardId, boardContentViewModel.getBoardId());
        assertEquals(4, boardContentViewModel.getFigureDtos().size());

    }
}
