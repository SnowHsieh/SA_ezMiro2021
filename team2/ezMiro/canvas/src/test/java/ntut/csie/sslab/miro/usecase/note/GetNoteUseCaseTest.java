package ntut.csie.sslab.miro.usecase.note;

import ntut.csie.sslab.miro.adapter.presenter.note.GetNotePresenter;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.usecase.AbstractUseCaseTest;
import ntut.csie.sslab.miro.usecase.note.get.GetNoteInput;
import ntut.csie.sslab.miro.usecase.note.get.GetNoteUseCase;
import ntut.csie.sslab.miro.usecase.note.get.GetNoteUseCaseImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GetNoteUseCaseTest extends AbstractUseCaseTest {
    @Test
    public void get_note_by_note_id() {
        String boardId = create_board();
        Coordinate coordinate = new Coordinate(5,6);
        String noteId = create_note(boardId, coordinate);
        GetNoteUseCase getNoteUseCase = new GetNoteUseCaseImpl(figureRepository);
        GetNoteInput input = (GetNoteInput) getNoteUseCase;
        input.setNoteId(noteId);
        GetNotePresenter output = new GetNotePresenter();

        getNoteUseCase.execute(input, output);

        assertEquals("", output.getNote().getDescription());
        assertEquals("#6FB7B7", output.getNote().getColor());
        assertEquals(100, output.getNote().getWidth());
        assertEquals(100, output.getNote().getHeight());
        assertEquals(5, output.getNote().getCoordinate().getX());
        assertEquals(6, output.getNote().getCoordinate().getY());
    }

    @Test
    public void get_notes_by_board_id() {
        String boardId = create_board();
        Coordinate coordinate1 = new Coordinate(5,6);
        Coordinate coordinate2 = new Coordinate(10,8);
        String note1Id = create_note(boardId, coordinate1);
        String note2Id = create_note(boardId, coordinate2);
        GetNoteUseCase getNoteUseCase = new GetNoteUseCaseImpl(figureRepository);
        GetNoteInput input = (GetNoteInput) getNoteUseCase;
        input.setBoardId(boardId);
        GetNotePresenter output = new GetNotePresenter();

        getNoteUseCase.execute(input, output);

        assertEquals(2, output.getNotes().size());
        NoteDTO note1Dto = output.getNotes().stream().filter(x -> x.getNoteId().equals(note1Id)).findFirst().get();
        assertEquals("", note1Dto.getDescription());
        assertEquals("#6FB7B7", note1Dto.getColor());
        assertEquals(100, note1Dto.getWidth());
        assertEquals(100, note1Dto.getHeight());
        assertEquals(5, note1Dto.getCoordinate().getX());
        assertEquals(6, note1Dto.getCoordinate().getY());
        NoteDTO note2Dto = output.getNotes().stream().filter(x -> x.getNoteId().equals(note2Id)).findFirst().get();
        assertEquals("", note2Dto.getDescription());
        assertEquals("#6FB7B7", note2Dto.getColor());
        assertEquals(100, note2Dto.getWidth());
        assertEquals(100, note2Dto.getHeight());
        assertEquals(10, note2Dto.getCoordinate().getX());
        assertEquals(8, note2Dto.getCoordinate().getY());
    }

    @Test
    public void get_notes_by_board_id_and_note_id() {
        String boardId = create_board();
        Coordinate coordinate1 = new Coordinate(5,6);
        Coordinate coordinate2 = new Coordinate(10,8);
        String note1Id = create_note(boardId, coordinate1);
        create_note(boardId, coordinate2);
        GetNoteUseCase getNoteUseCase = new GetNoteUseCaseImpl(figureRepository);
        GetNoteInput input = (GetNoteInput) getNoteUseCase;
        input.setBoardId("boardId");
        input.setNoteId(note1Id);
        GetNotePresenter output = new GetNotePresenter();

        getNoteUseCase.execute(input, output);

        assertEquals("", output.getNote().getDescription());
        assertEquals("#6FB7B7", output.getNote().getColor());
        assertEquals(100, output.getNote().getWidth());
        assertEquals(100, output.getNote().getHeight());
        assertEquals(5, output.getNote().getCoordinate().getX());
        assertEquals(6, output.getNote().getCoordinate().getY());
    }

    @Test
    public void get_notes_without_board_id_and_note_id() {
        String boardId = create_board();
        Coordinate coordinate1 = new Coordinate(5,6);
        Coordinate coordinate2 = new Coordinate(10,8);
        create_note(boardId, coordinate1);
        create_note(boardId, coordinate2);
        GetNoteUseCase getNoteUseCase = new GetNoteUseCaseImpl(figureRepository);
        GetNoteInput input = (GetNoteInput) getNoteUseCase;
        GetNotePresenter output = new GetNotePresenter();
        try {
            getNoteUseCase.execute(input, output);
            fail();
        } catch(IllegalArgumentException e) {
            assertEquals("boardId and noteId cannot be null.", e.getMessage());
        }
    }
}