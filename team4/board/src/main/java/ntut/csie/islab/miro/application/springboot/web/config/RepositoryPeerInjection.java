package ntut.csie.islab.miro.application.springboot.web.config;

import ntut.csie.islab.miro.adapter.gateway.repository.board.BoardRepositoryPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class RepositoryPeerInjection {
    @Autowired
    private BoardRepositoryPeer boardRepositoryPeer;

//    @Bean(name = "repositoryPeer")
//    public RepositoryPeer getInMemoryPeer() {
//        return new InMemoryAggregateRootRepositoryPeer();
//    }

}
