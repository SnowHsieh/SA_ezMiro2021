package ntut.csie.islab.miro.application.springboot.web.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import javax.sql.DataSource;
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ntut.csie.islab.miro.adapter.gateway.repository",
        entityManagerFactoryRef = "miroEntityManagerFactory",
        transactionManagerRef= "miroTransactionManager"
)
public class MIRODataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.miro")
    public DataSourceProperties miroDataSourceProperties() {

        System.out.println("miroDataSourceProperties flow");
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.miro.configuration")
    public DataSource miroDataSource() {
        System.out.println("miroDataSource flow");
        return miroDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();

    }

    @Bean(name = "miroEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean miroEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        System.out.println("miroEntityManagerFactory flow");
        return builder
                .dataSource(miroDataSource())
                .packages("ntut.csie.islab.miro.adapter.gateway.repository")
                .build();
    }

    @Bean
    public PlatformTransactionManager miroTransactionManager(
            final @Qualifier("miroEntityManagerFactory") LocalContainerEntityManagerFactoryBean miroEntityManagerFactory) {
        System.out.println("PlatformTransactionManager flow");

        return new JpaTransactionManager(miroEntityManagerFactory.getObject());
    }
}
