package ntut.csie.islab.miro.usecase.scenario;

import ntut.csie.islab.miro.entity.model.figure.textfigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.textfigure.Style;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateAndEditFourStickyNotesTest extends AbstractSpringBootJpaTest {

    @Test
    public void create_four_yellow_stickyNotes_and_edit_four_stickyNotes_test() {

        CqrsCommandPresenter domainEventStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(20,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#f28500"));
        assertNotNull(domainEventStickyNote.getId());
        assertEquals(ExitCode.SUCCESS,domainEventStickyNote.getExitCode());

        assertEquals(boardId,stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getBoardId());
        assertEquals(20, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getWidth());
        assertEquals("#f28500", stickyNoteRepository.findById(UUID.fromString(domainEventStickyNote.getId())).get().getStyle().getColor());

        CqrsCommandPresenter commandStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(10,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#0080ff"));
        assertEquals(boardId,stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getBoardId());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getStyle().getWidth());
        assertEquals("#0080ff", stickyNoteRepository.findById(UUID.fromString(commandStickyNote.getId())).get().getStyle().getColor());


        CqrsCommandPresenter readModelStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(0,10),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#28ff28"));

        assertEquals(boardId,stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getBoardId());
        assertEquals(0, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getPosition().getX());
        assertEquals(10, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getStyle().getWidth());
        assertEquals("#28ff28", stickyNoteRepository.findById(UUID.fromString(readModelStickyNote.getId())).get().getStyle().getColor());


        CqrsCommandPresenter aggregateStickyNote = generateCreateStickyNoteUseCaseOutput(
                boardId,
                new Position(15,0),
                "",
                new Style(12, ShapeKindEnum.RECTANGLE, 100,100, "#f9f900"));

        assertEquals(boardId,stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getBoardId());
        assertEquals(15, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getX());
        assertEquals(0, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getPosition().getY());
        assertEquals("", stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getContent());
        assertEquals(12, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getFontSize());
        assertEquals(ShapeKindEnum.RECTANGLE, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getShape());
        assertEquals(100, stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getWidth());
        assertEquals("#f9f900", stickyNoteRepository.findById(UUID.fromString(aggregateStickyNote.getId())).get().getStyle().getColor());

        //Change Content
        CqrsCommandPresenter changeDomainEventStickyNoteContentOutput = generateChangeStickyNoteContentUseCaseOutput(
                boardId,
                UUID.fromString(domainEventStickyNote.getId()),
                "sticky\n note \n created"
        );
        assertEquals("sticky\n note \n created", stickyNoteRepository.findById(UUID.fromString(changeDomainEventStickyNoteContentOutput.getId())).get().getContent());

        CqrsCommandPresenter changeCommandStickyNoteContentOutput = generateChangeStickyNoteContentUseCaseOutput(
                boardId,
                UUID.fromString(commandStickyNote.getId()),
                "Create\nsticky\nnote"
        );
        assertEquals("Create\nsticky\nnote" , stickyNoteRepository.findById(UUID.fromString(changeCommandStickyNoteContentOutput.getId())).get().getContent());

        CqrsCommandPresenter changeReadModelStickyNoteContentOutput = generateChangeStickyNoteContentUseCaseOutput(
                boardId,
                UUID.fromString(readModelStickyNote.getId()),
                "boardId\n" +
                        "position,\n" +
                        "content,\n" +
                        "style(fontSize,shape,width, height, color)"
        );
        assertEquals("boardId\n" +
                "position,\n" +
                "content,\n" +
                "style(fontSize,shape,width, height, color)" , stickyNoteRepository.findById(UUID.fromString(changeReadModelStickyNoteContentOutput.getId())).get().getContent());

        CqrsCommandPresenter changeAggregateStickyNoteContentOutput = generateChangeStickyNoteContentUseCaseOutput(
                boardId,
                UUID.fromString(aggregateStickyNote.getId()),
                "sticky\nnote"
        );
        assertEquals("sticky\nnote" , stickyNoteRepository.findById(UUID.fromString(changeAggregateStickyNoteContentOutput.getId())).get().getContent());


    }



}
