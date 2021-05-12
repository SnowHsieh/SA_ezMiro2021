package ntut.csie.sslab.kanban.application.springboot.web.config;

import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.cursor.CursorRepositoryImpl;
import ntut.csie.sslab.kanban.adapter.gateway.repository.springboot.figure.FigureRepositoryImpl;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("KanbanRepositoryInjection")
public class RepositoryInjection {
//
//  private BoardRepositoryPeer boardRepositoryPeer;
//
//  private WorkflowRepositoryPeer workflowRepositoryPeer;
//
//  private CardRepositoryPeer cardRepositoryPeer;
//
//  @Autowired
//  public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer){
//    this.boardRepositoryPeer = boardRepositoryPeer;
//  }
//
//  @Autowired
//  public void setWorkflowRepositoryPeer(WorkflowRepositoryPeer workflowRepositoryPeer) {
//    this.workflowRepositoryPeer = workflowRepositoryPeer;
//  }
//
//  @Autowired
//  public void setCardRepositoryPeer(CardRepositoryPeer cardRepositoryPeer) {
//    this.cardRepositoryPeer = cardRepositoryPeer;
//  }
//
//
  @Bean(name="figureRepository")
  public FigureRepository figureRepository() {
    return new FigureRepositoryImpl();
  }

  @Bean(name="boardRepository")
  public BoardRepository boardRepository() {
    return new BoardRepositoryImpl();
  }

  @Bean(name="cursorRepository")
  public CursorRepository cursorRepository() {
    return new CursorRepositoryImpl();
  }

  //
//  @Bean(name="workflowRepository")
//  public WorkflowRepository workflowRepository() {
//    return new WorkflowRepositoryImpl(workflowRepositoryPeer);
//  }
//
//  @Bean(name="cardRepository")
//  public CardRepository cardRepository() {
//    return new CardRepositoryImpl(cardRepositoryPeer);
//  }
//
//
  @Bean(name="kanbanEventBus")
  public DomainEventBus eventBus() {
    return new GoogleEventBus();
  }

}
