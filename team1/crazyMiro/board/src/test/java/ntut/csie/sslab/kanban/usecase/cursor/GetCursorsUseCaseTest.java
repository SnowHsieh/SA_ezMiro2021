package ntut.csie.sslab.kanban.usecase.cursor;

import ntut.csie.sslab.kanban.entity.model.Coordinate;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GetCursorsUseCaseTest extends AbstractSpringBootJpaTest {

    @Test
    public void get_cursors() {
        String userId1 = UUID.randomUUID().toString();
        String userId2 = UUID.randomUUID().toString();
        String boardId = createBoard(UUID.randomUUID().toString(), "boardName");
        Coordinate defaultPosition = new Coordinate(0, 0);
        enterBoard(boardId, userId1);
        enterBoard(boardId, userId2);

        List<Cursor> cursors = cursorRepository.getCursorsByBoardId(boardId);
        List<CursorDto> cursorDtos = ConvertCursorToDto.transform(cursors);

        assertEquals(2, cursorDtos.size());
        assertEquals(userId1, cursorDtos.get(0).getUserId());
        assertTrue(cursorDtos.get(0).getPosition().equals(defaultPosition));
        assertEquals(userId2, cursorDtos.get(1).getUserId());
        assertTrue(cursorDtos.get(1).getPosition().equals(defaultPosition));
    }
}
