package ntut.csie.selab.usecase.widget.query.getwidget;

import ntut.csie.selab.adapter.gateway.repository.springboot.widget.StickyNoteRepositoryPeer;
import ntut.csie.selab.adapter.presenter.widget.getwidget.StickyNoteViewModel;
import ntut.csie.selab.adapter.widget.StickyNoteRepositoryImpl;
import ntut.csie.selab.entity.model.widget.Position;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.usecase.JpaApplicationTest;
import ntut.csie.selab.usecase.widget.StickyNoteDto;
import ntut.csie.selab.usecase.widget.StickyNoteDtoMapper;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;
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
    private StickyNoteRepositoryPeer stickyNoteRepositoryPeer;

    @Test
    public void get_sticky_note_should_succeed() {
        // Arrange
        StickyNoteRepository stickyNoteRepository = new StickyNoteRepositoryImpl(stickyNoteRepositoryPeer);
        GetWidgetUseCase getWidgetUseCase = new GetWidgetUseCase(stickyNoteRepository);
        GetWidgetInput input = new GetWidgetInput();
        GetWidgetOutput output = new GetWidgetOutput();
        create_single_sticky_note(stickyNoteRepository);
        input.setWidgetId("stickyNoteId");
        StickyNoteDtoMapper stickyNoteDtoMapper = new StickyNoteDtoMapper();
        StickyNoteDto stickyNoteDto;

        // Act
        getWidgetUseCase.execute(input, output);
        stickyNoteDto = stickyNoteDtoMapper.domainToDto((StickyNote) output.getWidget());
        StickyNoteViewModel stickyNoteViewModel = new StickyNoteViewModel(stickyNoteDto);

        // Assert
        Assert.assertEquals("stickyNoteId", stickyNoteViewModel.getWidgetDto().getWidgetId());
    }

    private void create_single_sticky_note(StickyNoteRepository stickyNoteRepository) {
        String boardId = "boardId";
        String stickyNoteId = "stickyNoteId";

        Widget readModel = new StickyNote(stickyNoteId, boardId, new Position(0, 20, 10, 30));
        stickyNoteRepository.save(readModel);
    }
}
