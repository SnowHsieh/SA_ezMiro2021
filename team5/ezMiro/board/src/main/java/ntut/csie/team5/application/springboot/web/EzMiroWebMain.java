package ntut.csie.team5.application.springboot.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@ComponentScan(basePackages = {"ntut.csie.team5"})
@EntityScan(basePackages = {"ntut.csie.team5.adapter"})

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class EzMiroWebMain extends SpringBootServletInitializer implements CommandLineRunner {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EzMiroWebMain.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(EzMiroWebMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("EzMiroWebMain run");
    }
}
