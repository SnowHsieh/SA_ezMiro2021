package ntut.csie.team5.usecase.figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.adapter.figure.FigureRepositoryImpl;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.note.post.PostNoteInput;
import ntut.csie.team5.usecase.figure.note.post.PostNoteUseCase;
import ntut.csie.team5.usecase.figure.note.post.PostNoteUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PostNoteUseCaseTest {

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp() throws Exception {
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();
    }

    @Test
    public void should_succeed_when_post_note() {
        PostNoteUseCase postNoteUseCase = new PostNoteUseCaseImpl(figureRepository, domainEventBus);
        PostNoteInput postNoteInput = postNoteUseCase.newInput();
        CqrsCommandPresenter postNoteOutput = CqrsCommandPresenter.newInstance();

        postNoteInput.setBoardId("1");
        postNoteInput.setPosition(new Point(1, 1));
        postNoteInput.setColor(Color.RED);

        postNoteUseCase.execute(postNoteInput, postNoteOutput);

        assertNotNull(postNoteOutput.getId());
        assertEquals(ExitCode.SUCCESS, postNoteOutput.getExitCode());

        Note note = figureRepository.findById(postNoteOutput.getId()).get();
        assertEquals(postNoteOutput.getId(), note.getNoteId());
        assertEquals("1", note.getBoardId());
        assertEquals(new Point(1, 1), note.getPosition());
        assertEquals(Color.RED, note.getColor());
    }
}
