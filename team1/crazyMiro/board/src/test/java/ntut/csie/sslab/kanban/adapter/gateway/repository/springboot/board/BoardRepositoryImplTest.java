//package ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board;
//
//import ntut.csie.sslab.kanban.usecase.AbstractSpringBootJpaTest;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class BoardRepositoryImplTest extends AbstractSpringBootJpaTest {
//
//    @BeforeEach
//    public void setUp() {
//        super.setUp();
//    }
//
//    @Test
//    public void when_get_board_by_id_with_existing_id_then_the_result_is_present(){
//        boardId = UUID.randomUUID().toString();
//        Board board = new Board(teamId, boardId, boardName, userId);
//        boardRepository.save(board);
//        Assertions.assertTrue(boardRepository.findById(boardId).isPresent());
//    }
//
//
//    @Test
//    public void when_get_board_by_id_with_non_existing_id_then_the_result_is_not_present(){
//        Assertions.assertFalse(boardRepository.findById(UUID.randomUUID().toString()).isPresent());
//    }
//
//
//}
