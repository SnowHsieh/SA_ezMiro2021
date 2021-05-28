package ntut.csie.selab.usecase.widget.query.getwidget;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.WidgetRepositoryPeer;
import ntut.csie.selab.adapter.presenter.widget.getwidget.WidgetViewModel;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.widget.WidgetDto;
import ntut.csie.selab.usecase.widget.WidgetDtoMapper;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes= JpaApplicationTest.class)
@Rollback(false)
public class GetWidgetUseCaseTest {

    @Autowired
    private WidgetRepositoryPeer widgetRepositoryPeer;

    @Test
    public void get_sticky_note_should_succeed() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl(widgetRepositoryPeer);
        GetWidgetUseCase getWidgetUseCase = new GetWidgetUseCase(widgetRepository);
        GetWidgetInput input = new GetWidgetInput();
        GetWidgetOutput output = new GetWidgetOutput();
        create_single_sticky_note(widgetRepository);
        input.setWidgetId("stickyNoteId");
        WidgetDtoMapper widgetDtoMapper = new WidgetDtoMapper();
        WidgetDto widgetDto;

        // Act
        getWidgetUseCase.execute(input, output);
        widgetDto = widgetDtoMapper.domainToDto(output.getWidget());
        WidgetViewModel widgetViewModel = new WidgetViewModel(widgetDto);

        // Assert
        Assert.assertEquals("stickyNoteId", widgetViewModel.getWidgetDto().getWidgetId());
    }

    private void create_single_sticky_note(WidgetRepository widgetRepository) {
        String boardId = "boardId";
        String stickyNoteId = "stickyNoteId";

        Widget readModel = new StickyNote(stickyNoteId, boardId, new Coordinate(0, 20, 10, 30));
        widgetRepository.save(readModel);
    }
}
