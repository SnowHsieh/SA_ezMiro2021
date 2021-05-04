package ntut.csie.selab.application.springboot.web.config;

import ntut.csie.selab.adapter.board.BoardRepositoryImpl;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("MiroRepositoryInjection")
public class RepositoryInjection {

    @Bean(name="boardRepository")
    public BoardRepository boardRepository() { return new BoardRepositoryImpl(); }

    @Bean(name="widgetRepository")
    public WidgetRepository widgetRepository() { return new WidgetRepositoryImpl(); }

    @Bean(name="miroEventBus")
    public DomainEventBus eventBus() {
        return new DomainEventBus();
    }
}
