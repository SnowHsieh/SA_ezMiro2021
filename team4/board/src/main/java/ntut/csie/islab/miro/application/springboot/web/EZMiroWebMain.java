package ntut.csie.islab.miro.application.springboot.web;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"ntut.csie.islab.miro"})
@EntityScan(basePackages = {"ntut.csie.islab.miro.adapter"})

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})

public class EZMiroWebMain extends SpringBootServletInitializer implements CommandLineRunner {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EZMiroWebMain.class);

    }


    public static void main(String[] args) {
        SpringApplication.run(EZMiroWebMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Team4 EZMiroWebMain run");
    }



}
