package ntut.csie.team5.usecase.figure.note;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.adapter.figure.FigureRepositoryImpl;
import ntut.csie.team5.usecase.figure.note.delete.DeleteNoteInput;
import ntut.csie.team5.usecase.figure.note.delete.DeleteNoteUseCase;
import ntut.csie.team5.usecase.figure.note.delete.DeleteNoteUseCaseImpl;
import ntut.csie.team5.usecase.figure.note.post.PostNoteInput;
import ntut.csie.team5.usecase.figure.note.post.PostNoteUseCase;
import ntut.csie.team5.usecase.figure.note.post.PostNoteUseCaseImpl;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class DeleteNoteUseCaseTest {

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp() throws Exception {
        figureRepository = new FigureRepositoryImpl();
        domainEventBus = new DomainEventBus();
    }

    @Test
    public void should_succeed_when_delete_note() {
        PostNoteUseCase postNoteUseCase = new PostNoteUseCaseImpl(figureRepository, domainEventBus);
        PostNoteInput postNoteInput = postNoteUseCase.newInput();
        CqrsCommandPresenter postNoteOutput = CqrsCommandPresenter.newInstance();

        postNoteInput.setBoardId("1");
        postNoteInput.setPosition(new Point(1, 1));
        postNoteInput.setColor(Color.RED);

        postNoteUseCase.execute(postNoteInput, postNoteOutput);

        String noteId = postNoteOutput.getId();

        DeleteNoteUseCase deleteNoteUseCase = new DeleteNoteUseCaseImpl(figureRepository, domainEventBus);
        DeleteNoteInput deleteNoteInput = deleteNoteUseCase.newInput();
        CqrsCommandPresenter deleteNoteOutput = CqrsCommandPresenter.newInstance();

        deleteNoteInput.setBoardId("1");
        deleteNoteInput.setPosition(new Point(1, 1));
        deleteNoteInput.setColor(Color.RED);
        deleteNoteInput.setNoteId(noteId);

        deleteNoteUseCase.execute(deleteNoteInput, deleteNoteOutput);

        assertNotNull(deleteNoteOutput.getId());
        assertEquals(ExitCode.SUCCESS, deleteNoteOutput.getExitCode());
        assertFalse(figureRepository.findById(deleteNoteOutput.getId()).isPresent());
    }
}
