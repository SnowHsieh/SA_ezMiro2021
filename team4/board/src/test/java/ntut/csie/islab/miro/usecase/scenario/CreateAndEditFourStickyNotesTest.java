package ntut.csie.islab.miro.usecase.scenario;

import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.textFigure.*;
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

public class CreateAndEditFourStickyNotesTest {
    public DomainEventBus domainEventBus;
    public TextFigureRepository stickyNoteRepository;

    @BeforeEach
    public void setUp(){
            domainEventBus = new GoogleEventBus();
            stickyNoteRepository = new TextFigureRepository();
    }

    private CqrsCommandPresenter generateCreateStickyNoteUseCaseOutput(UUID id, Position position, String content, Style style){
        CreateStickyNoteUseCase createStickyNoteUseCase = new CreateStickyNoteUseCase(stickyNoteRepository, domainEventBus);
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
        EditStickyNoteUseCase editStickyNoteUseCase = new EditStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        EditStickyNoteInput editStickyNoteUseCaseInput = editStickyNoteUseCase.newInput();
        CqrsCommandPresenter editStickyNoteUseCaseOutput = CqrsCommandPresenter.newInstance();

        editStickyNoteUseCaseInput.setBoardId (boardId);
        editStickyNoteUseCaseInput.setFigureId(UUID.fromString(figureId));
        editStickyNoteUseCaseInput.setContent(content);
        editStickyNoteUseCaseInput.setStyle(style);
        editStickyNoteUseCase.execute(editStickyNoteUseCaseInput, editStickyNoteUseCaseOutput);

        return editStickyNoteUseCaseOutput;
    }


    @Test
    public void create_four_yellow_stickyNotes_and_edit_four_stickyNotes_test() {
        UUID boardId = UUID.randomUUID();

        CqrsCommandPresenter domainEventStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(20,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#f9f900"));
        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS,domainEventStickyNote.getExitCode());

        assertEquals(boardId,stickyNoteRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, stickyNoteRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getWidth());
        assertEquals("#f9f900", stickyNoteRepository.findById(boardId,UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

        CqrsCommandPresenter commandStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(10,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#f9f900"));
        assertEquals(boardId,stickyNoteRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getBoardId());
        assertEquals(10, stickyNoteRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getWidth());
        assertEquals("#f9f900", stickyNoteRepository.findById(boardId,UUID.fromString(commandStickyNote.getId())).get().getStyle().getColor());


        CqrsCommandPresenter readModelStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(0,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#f9f900"));

        assertEquals(boardId,stickyNoteRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getBoardId());
        assertEquals(0, stickyNoteRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getWidth());
        assertEquals("#f9f900", stickyNoteRepository.findById(boardId,UUID.fromString(readModelStickyNote.getId())).get().getStyle().getColor());


        CqrsCommandPresenter aggregateStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(15,0),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#f9f900"));

        assertEquals(boardId,stickyNoteRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getBoardId());
        assertEquals(15, stickyNoteRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getX());
        assertEquals(0, stickyNoteRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getWidth());
        assertEquals("#f9f900", stickyNoteRepository.findById(boardId,UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getColor());

        CqrsCommandPresenter editDomainEventStickyNoteOutput = editStickyNoteUseCaseOutput(
                boardId,
                domainEventStickyNote.getId(),
                "sticky\n note \n created",
                new  Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#f28500")
        );
        CqrsCommandPresenter editCommandStickyNoteOutput = editStickyNoteUseCaseOutput(
                boardId,
                commandStickyNote.getId(),
                "getcontent\n" +
                        "\n" +
                        "sticky\n" +
                        "\n" +
                        "note",
                new  Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#0080ff")
        );
        CqrsCommandPresenter editReadModelStickyNoteOutput = editStickyNoteUseCaseOutput(
                boardId,
                readModelStickyNote.getId(),
                "stickNoteId,\n" +
                        "\n" +
                        "content,\n" +
                        "\n" +
                        "style(font,shape,size,color)",
                new  Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#28ff28")
        );
        CqrsCommandPresenter editAggregateStickyNoteOutput = editStickyNoteUseCaseOutput(
                boardId,
                aggregateStickyNote.getId(),
                "sticky note",
                new  Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#f9f900")
        );

        assertEquals("sticky\n note \n created", stickyNoteRepository.findById(boardId,UUID.fromString(editDomainEventStickyNoteOutput.getId())).get().getContent());
        assertEquals("#f28500", stickyNoteRepository.findById(boardId,UUID.fromString(editDomainEventStickyNoteOutput.getId())).get().getStyle().getColor());

        assertEquals("getcontent\n" +
                "\n" +
                "sticky\n" +
                "\n" +
                "note", stickyNoteRepository.findById(boardId,UUID.fromString(editCommandStickyNoteOutput.getId())).get().getContent());
        assertEquals("#0080ff", stickyNoteRepository.findById(boardId,UUID.fromString(editCommandStickyNoteOutput.getId())).get().getStyle().getColor());

        assertEquals("stickNoteId,\n" +
                "\n" +
                "content,\n" +
                "\n" +
                "style(font,shape,size,color)", stickyNoteRepository.findById(boardId,UUID.fromString(editReadModelStickyNoteOutput.getId())).get().getContent());
        assertEquals("#28ff28", stickyNoteRepository.findById(boardId,UUID.fromString(editReadModelStickyNoteOutput.getId())).get().getStyle().getColor());

        assertEquals("sticky note", stickyNoteRepository.findById(boardId,UUID.fromString(editAggregateStickyNoteOutput.getId())).get().getContent());
        assertEquals("#f9f900", stickyNoteRepository.findById(boardId,UUID.fromString(editAggregateStickyNoteOutput.getId())).get().getStyle().getColor());
    }



}
