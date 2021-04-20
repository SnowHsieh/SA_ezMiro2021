package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board2;

import ntut.csie.sslab.kanban.entity.model.board2.Board2;
import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Board22RepositoryImplTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void when_get_board_by_id_with_existing_id_then_the_result_is_present(){
        boardId = UUID.randomUUID().toString();
        Board2 board2 = new Board2(teamId, boardId, boardName, userId);
        board2Repository.save(board2);
        Assertions.assertTrue(board2Repository.findById(boardId).isPresent());
    }


    @Test
    public void when_get_board_by_id_with_non_existing_id_then_the_result_is_not_present(){
        Assertions.assertFalse(board2Repository.findById(UUID.randomUUID().toString()).isPresent());
    }


}
