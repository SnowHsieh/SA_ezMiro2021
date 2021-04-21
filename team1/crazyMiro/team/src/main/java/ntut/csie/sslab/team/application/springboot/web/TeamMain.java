package ntut.csie.sslab.team.application.springboot.web;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"ntut.csie.sslab.team"})
@EntityScan(basePackages={"ntut.csie.sslab.team.adapter.gateway"})
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class TeamMain extends SpringBootServletInitializer implements CommandLineRunner {

    private DomainEventBus domainEventBus;

    @Autowired
    public void setDomainEventBus(DomainEventBus domainEventBus) {
        this.domainEventBus = domainEventBus;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TeamMain.class);
    }

    public static void main(String[] args){
        SpringApplication.run(TeamMain.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        System.out.println("TeamMain run");
    }
}
