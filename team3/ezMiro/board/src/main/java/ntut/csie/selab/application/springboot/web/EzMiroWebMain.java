package ntut.csie.selab.application.springboot.web;
import ntut.csie.selab.adapter.controller.rest.springboot.board.getcontent.GetBoardContentController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackageClasses = GetBoardContentController.class)
public class EzMiroWebMain {
    public static void main(String[] args) {
        SpringApplication.run(EzMiroWebMain.class, args);
    }
}
