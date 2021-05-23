package ntut.csie.sslab.miro.application.springboot.web.config;

import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.cursor.CursorRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.FigureRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.FigureRepositoryPeer;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
import ntut.csie.sslab.miro.usecase.figure.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("MiroRepositoryInjection")
public class RepositoryInjection {
//
  private BoardRepositoryPeer boardRepositoryPeer;

  private FigureRepositoryPeer figureRepositoryPeer;
//
//  private WorkflowRepositoryPeer workflowRepositoryPeer;
//
//  private CardRepositoryPeer cardRepositoryPeer;
//
  @Autowired
  public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer){
    this.boardRepositoryPeer = boardRepositoryPeer;
  }

  @Autowired
  public void setFigureRepositoryPeer(FigureRepositoryPeer figureRepositoryPeer){
    this.figureRepositoryPeer = figureRepositoryPeer;
  }


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
    return new FigureRepositoryImpl(figureRepositoryPeer);
  }

  @Bean(name="boardRepository")
  public BoardRepository boardRepository() {
    return new BoardRepositoryImpl(boardRepositoryPeer);
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
