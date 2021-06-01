//package ntut.csie.sslab.miro.usecase.eventhandler;
//
//import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
//import ntut.csie.sslab.ddd.model.DomainEventBus;
//import ntut.csie.sslab.miro.adapter.broadcaster.FakeEventBroadcaster;
//import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
//import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.cursor.CursorRepositoryImpl;
//import ntut.csie.sslab.miro.entity.model.cursor.event.CursorEvents;
//import ntut.csie.sslab.miro.entity.model.note.Coordinate;
//import ntut.csie.sslab.miro.usecase.board.BoardRepository;
//import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
//import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorInput;
//import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorUseCase;
//import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorUseCaseImpl;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import static org.awaitility.Awaitility.await;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class EventBroadcasterTest {
//    private DomainEventBus domainEventBus;
//    private BoardRepository boardRepository;
//    private CursorRepository cursorRepository;
//    private NotifyEventBroadcaster notifyEventBroadcaster;
//    public FakeEventBroadcaster fakeEventBroadcaster;
//    public ExecutorService executor;
//
//    @BeforeEach
//    public void setUp(){
//        executor = Executors.newFixedThreadPool(1);
//        domainEventBus = new DomainEventBus();
//        boardRepository = new BoardRepositoryImpl();
//        cursorRepository = new CursorRepositoryImpl();
//        fakeEventBroadcaster = new FakeEventBroadcaster();
//        notifyEventBroadcaster = new NotifyEventBroadcaster(
//                                            fakeEventBroadcaster,
//                                            boardRepository,
//                                            cursorRepository,
//                                            executor);
//
//        domainEventBus.register(notifyEventBroadcaster);
//    }
//
//    @Test
//    public void should_broadcast_cursor_created_domain_event_model_when_create_cursor(){
//        createCursor();
//        await().untilAsserted(() -> Assertions.assertThat(fakeEventBroadcaster.getRemoteDomainEvents().size()).isEqualTo(1));
//        assertEquals(CursorEvents.CursorCreated.class.getSimpleName(), fakeEventBroadcaster.getRemoteDomainEvents().get(0).getEventSimpleName());
//    }
//
//    private void createCursor() {
//        CreateCursorUseCase createCursorUseCase = new CreateCursorUseCaseImpl(cursorRepository, domainEventBus);
//        CreateCursorInput input = createCursorUseCase.newInput();
//        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
//        input.setBoardId("boardId");
//        input.setUserId("userId");
//        input.setCoordinate(new Coordinate(3, 4));
//
//        createCursorUseCase.execute(input, output);
//    }
//}