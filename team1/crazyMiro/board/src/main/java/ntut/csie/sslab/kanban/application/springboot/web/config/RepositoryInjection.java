package ntut.csie.sslab.kanban.application.springboot.web.config;

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
//  @Bean(name="boardRepository")
//  public BoardRepository boardRepository() {
//    return new BoardRepositoryImpl(boardRepositoryPeer);
//  }
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
//  @Bean(name="kanbanEventBus")
//  public DomainEventBus eventBus() {
//    return new GoogleEventBus();
//  }

}
