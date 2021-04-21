package ntut.csie.sslab.ezkanban.application.springboot.web.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ntut.csie.sslab.kanban",
        entityManagerFactoryRef = "kanbanEntityManagerFactory",
        transactionManagerRef= "kanbanTransactionManager"
)
public class KanbanDataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.kanban")
    public DataSourceProperties kanbanDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.kanban.configuration")
    public DataSource kanbanDataSource() {
        return kanbanDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();

    }

    @Bean(name = "kanbanEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kanbanEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(kanbanDataSource())
                .packages("ntut.csie.sslab.kanban.adapter.gateway.repository")
                .build();
    }

    @Bean
    public PlatformTransactionManager kanbanTransactionManager(
            final @Qualifier("kanbanEntityManagerFactory") LocalContainerEntityManagerFactoryBean kanbanEntityManagerFactory) {
        return new JpaTransactionManager(kanbanEntityManagerFactory.getObject());
    }
}
