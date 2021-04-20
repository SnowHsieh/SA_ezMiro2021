package ntut.csie.sslab.kanban.application.springboot.web.config;

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
