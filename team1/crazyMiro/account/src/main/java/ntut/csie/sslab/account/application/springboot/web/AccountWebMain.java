package ntut.csie.sslab.account.application.springboot.web;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@ComponentScan(basePackages = {"ntut.csie.sslab.account"})
@EntityScan(basePackages={"ntut.csie.sslab.account"})
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class AccountWebMain extends SpringBootServletInitializer implements CommandLineRunner {

    private DomainEventBus domainEventBus;

    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AccountWebMain.class);
    }

    public static void main(String[] args){
        SpringApplication.run(AccountWebMain.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        System.out.println("AccountWebMain run");
    }
}
