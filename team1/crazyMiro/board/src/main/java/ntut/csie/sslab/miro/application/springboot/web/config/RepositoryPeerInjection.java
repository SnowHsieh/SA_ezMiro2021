package ntut.csie.sslab.miro.application.springboot.web.config;

import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.FigureRepositoryImpl;
import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.figure.FigureRepositoryPeer;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.figure.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class RepositoryPeerInjection {

//  @Autowired
//  private BoardRepositoryPeer boardRepositoryPeer;

//  @Bean(name="repositoryPeer")
//  public RepositoryPeer getInMemoryPeer() {
//    return new InMemoryAggregateRootRepositoryPeer();
//  }


}
