package ntut.csie.team5.application.springboot.web.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ntut.csie.team5.adapter.gateway.repository",
        entityManagerFactoryRef = "ezMiroEntityManagerFactory",
        transactionManagerRef= "ezMiroTransactionManager"
)
public class EzMiroDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.kanban")
    public DataSourceProperties ezMiroDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.kanban.configuration")
    public DataSource ezMiroDataSource() {
        return ezMiroDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();

    }

    @Bean(name = "ezMiroEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean ezMiroEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(ezMiroDataSource())
                .packages("ntut.csie.team5.adapter.gateway.repository")
                .build();
    }

    @Bean
    public PlatformTransactionManager ezMiroTransactionManager(
            final @Qualifier("ezMiroEntityManagerFactory") LocalContainerEntityManagerFactoryBean ezMiroEntityManagerFactory) {
        return new JpaTransactionManager(ezMiroEntityManagerFactory.getObject());
    }
}
