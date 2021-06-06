package ntut.csie.sslab.miro.application.springboot.web.config;

import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.line.LineRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.sticker.StickerRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.sticker.StickerRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.line.LineRepositoryImpl;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.figure.StickerRepository;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:/application.properties")
@Configuration("MiroRepositoryInjection")
public class RepositoryInjection {

  private BoardRepositoryPeer boardRepositoryPeer;

  private StickerRepositoryPeer stickerRepositoryPeer;

  private LineRepositoryPeer lineRepositoryPeer;

  @Autowired
  public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer){
    this.boardRepositoryPeer = boardRepositoryPeer;
  }

  @Autowired
  public void setFigureRepositoryPeer(StickerRepositoryPeer stickerRepositoryPeer){
    this.stickerRepositoryPeer = stickerRepositoryPeer;
  }

  @Autowired
  public void setLineRepositoryPeer(LineRepositoryPeer lineRepositoryPeer){
    this.lineRepositoryPeer = lineRepositoryPeer;
  }


//
//  private WorkflowRepositoryPeer workflowRepositoryPeer;
//
//  private CardRepositoryPeer cardRepositoryPeer;
//

//  @Autowired
//  public void setBoardMdbRepositoryPeer(BoardRepositoryMongoDbPeer boardRepositoryMongoDbPeer){
//    this.boardRepositoryMongoDbPeer = boardRepositoryMongoDbPeer;
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
  public StickerRepository figureRepository() {
    return new StickerRepositoryImpl(stickerRepositoryPeer);
  }

  @Bean(name="boardRepository")
  public BoardRepository boardRepository() {
    return new BoardRepositoryImpl(boardRepositoryPeer);
  }

  @Bean(name="lineRepository")
  public LineRepository lineRepository() {
    return new LineRepositoryImpl(lineRepositoryPeer);
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
