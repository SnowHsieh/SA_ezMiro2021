package ntut.csie.selab.usecase.widget.query.getwidget;

import ntut.csie.selab.adapter.presenter.widget.getwidget.WidgetViewModel;
import ntut.csie.selab.adapter.widget.WidgetRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Coordinate;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.widget.WidgetDto;
import ntut.csie.selab.usecase.widget.WidgetMapper;
import ntut.csie.selab.usecase.widget.WidgetRepository;
import org.junit.Assert;
import org.junit.Test;

public class GetWidgetUseCaseTest {

    @Test
    public void get_sticky_note_should_succeed() {
        // Arrange
        WidgetRepository widgetRepository = new WidgetRepositoryImpl();
        GetWidgetUseCase getWidgetUseCase = new GetWidgetUseCase(widgetRepository);
        GetWidgetInput input = new GetWidgetInput();
        GetWidgetOutput output = new GetWidgetOutput();
        create_single_sticky_note(widgetRepository);
        input.setWidgetId("stickyNoteId");
        WidgetMapper widgetMapper = new WidgetMapper();
        WidgetDto widgetDto;

        // Act
        getWidgetUseCase.execute(input, output);
        widgetDto = widgetMapper.domainToDto(output.getWidget());
        WidgetViewModel widgetViewModel = new WidgetViewModel(widgetDto);

        // Assert
        Assert.assertEquals("stickyNoteId", widgetViewModel.getWidgetDto().getWidgetId());
    }

    private void create_single_sticky_note(WidgetRepository widgetRepository) {
        String boardId = "boardId";
        String stickyNoteId = "stickyNoteId";

        Widget readModel = new StickyNote(stickyNoteId, boardId, new Coordinate(0, 20, 10, 30));
        widgetRepository.add(readModel);
    }
}
