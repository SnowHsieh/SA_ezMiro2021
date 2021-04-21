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
@EnableJpaRepositories(basePackages = "ntut.csie.sslab.account",
        entityManagerFactoryRef = "accountEntityManagerFactory",
        transactionManagerRef= "accountTransactionManager"
)

public class AccountDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.account")
    public DataSourceProperties accountDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.account.configuration")
    public DataSource accountDataSource() {
        return accountDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();

    }


    @Primary
    @Bean(name = "accountEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean accountEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(accountDataSource())
                .packages("ntut.csie.sslab.account.users.command.adapter.repository" )
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager accountTransactionManager(
            final @Qualifier("accountEntityManagerFactory") LocalContainerEntityManagerFactoryBean accountEntityManagerFactory) {
        return new JpaTransactionManager(accountEntityManagerFactory.getObject());
    }
}
