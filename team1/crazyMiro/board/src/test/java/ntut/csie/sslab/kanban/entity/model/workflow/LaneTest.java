package ntut.csie.sslab.kanban.entity.model.workflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LaneTest {

    private Stage rootStage;
    private Lane swimLane1;
    private Lane swimLane2;
    private Lane swimLane2a;
    private Lane swimLane2b;
    private String cardId1;
    private String cardId2;
    private String cardId3;

    @BeforeEach
    public void setUp() {

        rootStage = new Stage(
                "rootStage id",
                "workflow id",
                NullLane.newInstance(),
                "To Do",
                WipLimit.valueOf(-1),
                0,
                LaneType.Standard);

        swimLane1 = rootStage.createSwimLane(
                    "swimlane id 1",
                        -1,
                        LaneType.Standard);

        swimLane2 = rootStage.createSwimLane(
                  "swimlane id 2",
                    -1,
                    LaneType.Standard);

        swimLane2a = swimLane2.createSwimLane(
                "swimlane id 2a",
                -1,
                LaneType.Standard);

        swimLane2b =swimLane2.createSwimLane(
                "swimlane id 2b",
                -1,
                LaneType.Standard);

        cardId1 = "A";
        cardId2 = "B";
        cardId3 = "C";

        rootStage.addCard(cardId1);
        rootStage.addCard(cardId2);
        rootStage.addCard(cardId3);
    }

    @Test
    public void should_have_correct_card_order_when_add_three_cards () {

        assertEquals(0, rootStage.getCardById(cardId1).get().getOrder());
        assertEquals(1, rootStage.getCardById(cardId2).get().getOrder());
        assertEquals(2, rootStage.getCardById(cardId3).get().getOrder());
    }

    @Test
    public void should_have_correct_card_order_when_add_a_card_at_position_0 () {

        String cardId4 = "B-2";
        rootStage.addCard(cardId4, 0);

        assertEquals(0, rootStage.getCardById(cardId4).get().getOrder());
        assertEquals(1, rootStage.getCardById(cardId1).get().getOrder());
        assertEquals(2, rootStage.getCardById(cardId2).get().getOrder());
        assertEquals(3, rootStage.getCardById(cardId3).get().getOrder());
    }


    @Test
    public void should_have_correct_card_order_when_remove_the_first_card () {

        rootStage.removeCard(cardId1);

        assertEquals(0, rootStage.getCardById(cardId2).get().getOrder());
        assertEquals(1, rootStage.getCardById(cardId3).get().getOrder());
    }


    @Test
    public void should_succeed_when_get_lane_by_id () {

        assertTrue(rootStage.getLaneById(rootStage, rootStage.getId()).isPresent());
        assertTrue(rootStage.getLaneById(rootStage, swimLane1.getId()).isPresent());
        assertTrue(rootStage.getLaneById(rootStage, swimLane2.getId()).isPresent());
        assertTrue(rootStage.getLaneById(rootStage, swimLane2a.getId()).isPresent());
        assertTrue(rootStage.getLaneById(rootStage, swimLane2b.getId()).isPresent());

        assertTrue(rootStage.getLaneById(swimLane1, swimLane1.getId()).isPresent());
        assertFalse(rootStage.getLaneById(swimLane1, swimLane2.getId()).isPresent());
        assertFalse(rootStage.getLaneById(swimLane1, swimLane2a.getId()).isPresent());
        assertFalse(rootStage.getLaneById(swimLane1, swimLane2b.getId()).isPresent());

        assertTrue(rootStage.getLaneById(swimLane2, swimLane2.getId()).isPresent());
        assertTrue(rootStage.getLaneById(swimLane2, swimLane2a.getId()).isPresent());
        assertTrue(rootStage.getLaneById(swimLane2, swimLane2b.getId()).isPresent());

    }

}
