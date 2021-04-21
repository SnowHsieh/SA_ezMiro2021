//package ntut.csie.sslab.ezkanban;
//
//import ntut.csie.sslab.account.application.springboot.web.AccountWebMain;
//import ntut.csie.sslab.account.application.springboot.web.config.AccountDataSourceConfiguration;
//import ntut.csie.sslab.account.application.springboot.web.config.AccountEventBusInjection;
//import ntut.csie.sslab.ddd.model.DomainEventBus;
//import ntut.csie.sslab.miro.adapter.gateway.repository.springboot.board.BoardRepositoryPeer;
//import ntut.csie.sslab.miro.application.springboot.web.EzKanbanWebMain;
//import ntut.csie.sslab.miro.application.springboot.web.config.KanbanDataSourceConfiguration;
//import ntut.csie.sslab.miro.usecase.eventhandler.*;
//import ntut.csie.sslab.team.application.springboot.web.TeamMain;
//import ntut.csie.sslab.team.application.springboot.web.config.TeamEventBusInjection;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//
//@ComponentScan(
//        basePackages = {"ntut.csie.sslab.miro", "ntut.csie.sslab.account", "ntut.csie.sslab.team","ntut.csie.sslab.ezkanban"}, excludeFilters = {
//        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= AccountEventBusInjection.class),
//        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= TeamEventBusInjection.class),
//        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= AccountWebMain.class),
//        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= EzKanbanWebMain.class),
//        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= TeamMain.class),
//        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= KanbanDataSourceConfiguration.class),
//        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value= AccountDataSourceConfiguration.class),
//})
//
//@EntityScan(basePackages={"ntut.csie.sslab.miro", "ntut.csie.sslab.account", "ntut.csie.sslab.team"})
//@SpringBootApplication(exclude = {
//        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
//)
//public class MonoMain extends SpringBootServletInitializer implements CommandLineRunner {
//
//    private DomainEventBus domainEventBus;
//    private NotifyBoard notifyBoard;
//    private NotifyWorkflow notifyWorkflow;
//    private BoardRepositoryPeer boardRepositoryPeer;
//
//    @Autowired
//    public void setDomainEventBus(@Qualifier("kanbanEventBus") DomainEventBus domainEventBus) {
//        this.domainEventBus = domainEventBus;
//    }
//
//    @Autowired
//    public void setNotifyBoard(NotifyBoard notifyBoard) { this.notifyBoard = notifyBoard; }
//
//    @Autowired
//    public void setNotifyWorkflow(NotifyWorkflow notifyWorkflow) { this.notifyWorkflow = notifyWorkflow; }
//
//
//    @Autowired
//    public void setBoardRepositoryPeer(BoardRepositoryPeer boardRepositoryPeer) {
//        this.boardRepositoryPeer = boardRepositoryPeer;
//    }
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(MonoMain.class);
//    }
//
//    public static void main(String[] args){
//        SpringApplication.run(MonoMain.class, args);
//    }
//
//    @Override
//    public void run(String... arg0) {
//        System.out.println("MonoMain run");
//        domainEventBus.register(notifyBoard);
//        domainEventBus.register(notifyWorkflow);
//    }
//}
