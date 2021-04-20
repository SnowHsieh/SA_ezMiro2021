package ntut.csie.sslab.ezkanban.application.springboot.web.config;

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
@EnableJpaRepositories(basePackages = "ntut.csie.sslab.team",
        entityManagerFactoryRef = "teamEntityManagerFactory",
        transactionManagerRef= "teamTransactionManager"
)
public class TeamDataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.team")
    public DataSourceProperties teamDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.team.configuration")
    public DataSource teamDataSource() {
        return teamDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();

    }



    @Bean(name = "teamEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean teamEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(teamDataSource())
                .packages("ntut.csie.sslab.team.adapter.gateway.repository")
                .build();
    }


    @Bean
    public PlatformTransactionManager teamTransactionManager(
            final @Qualifier("teamEntityManagerFactory") LocalContainerEntityManagerFactoryBean teamEntityManagerFactory) {
        return new JpaTransactionManager(teamEntityManagerFactory.getObject());
    }


}
