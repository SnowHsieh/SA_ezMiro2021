package ntut.csie.team5.application.springboot.web.config;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.team5.usecase.board.BoardRepository;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureZOrderUseCase;
import ntut.csie.team5.usecase.board.change_order.ChangeFigureZOrderUseCaseImpl;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCase;
import ntut.csie.team5.usecase.board.create.CreateBoardUseCaseImpl;
import ntut.csie.team5.usecase.board.enter.EnterBoardUseCase;
import ntut.csie.team5.usecase.board.enter.EnterBoardUseCaseImpl;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentUseCase;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentUseCaseImpl;
import ntut.csie.team5.usecase.board.leave.LeaveBoardUseCase;
import ntut.csie.team5.usecase.board.leave.LeaveBoardUseCaseImpl;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorUseCase;
import ntut.csie.team5.usecase.board.move_cursor.MoveCursorUseCaseImpl;
import ntut.csie.team5.usecase.figure.connectable_figure.note.NoteRepository;
import ntut.csie.team5.usecase.figure.connectable_figure.note.change_color.ChangeNoteColorUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.change_color.ChangeNoteColorUseCaseImpl;
import ntut.csie.team5.usecase.figure.connectable_figure.note.delete.DeleteNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.delete.DeleteNoteUseCaseImpl;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text.EditNoteTextUseCaseImpl;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.move.MoveNoteUseCaseImpl;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.post.PostNoteUseCaseImpl;
import ntut.csie.team5.usecase.figure.connectable_figure.note.resize.ResizeNoteUseCase;
import ntut.csie.team5.usecase.figure.connectable_figure.note.resize.ResizeNoteUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("EzMiroUserCaseInjection")
public class UseCaseInjection {

    private BoardRepository boardRepository;
    private NoteRepository noteRepository;
    private DomainEventBus eventBus;

    @Bean(name = "createBoardUseCase")
    public CreateBoardUseCase createBoardUseCase() {
        return new CreateBoardUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name = "getBoardContentUseCase")
    public GetBoardContentUseCase getBoardContentUseCase() {
        return new GetBoardContentUseCaseImpl(boardRepository, noteRepository, eventBus);
    }

    @Bean(name = "enterBoardUseCase")
    public EnterBoardUseCase enterBoardUseCase() {
        return new EnterBoardUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name = "leaveBoardUseCase")
    public LeaveBoardUseCase leaveBoardUseCase() {
        return new LeaveBoardUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name = "moveCursorUseCase")
    public MoveCursorUseCase moveCursorUseCase() {
        return new MoveCursorUseCaseImpl(boardRepository, eventBus);
    }

    @Bean(name = "postNoteUseCase")
    public PostNoteUseCase postNoteUseCase() {
        return new PostNoteUseCaseImpl(noteRepository, eventBus);
    }

    @Bean(name = "moveNoteUseCase")
    public MoveNoteUseCase moveNoteUseCase() {
        return new MoveNoteUseCaseImpl(noteRepository, eventBus);
    }

    @Bean(name = "changeNoteColorUseCase")
    public ChangeNoteColorUseCase changeNoteColorUseCase() {
        return new ChangeNoteColorUseCaseImpl(noteRepository, eventBus);
    }

    @Bean(name = "editNoteTextUseCase")
    public EditNoteTextUseCase editNoteTextUseCase() {
        return new EditNoteTextUseCaseImpl(noteRepository, eventBus);
    }

    @Bean(name = "resizeNoteUseCase")
    public ResizeNoteUseCase resizeNoteUseCase() {
        return new ResizeNoteUseCaseImpl(noteRepository, eventBus);
    }

    @Bean(name = "deleteNoteUseCase")
    public DeleteNoteUseCase deleteNoteUseCase() {
        return new DeleteNoteUseCaseImpl(noteRepository, eventBus);
    }

    @Bean(name = "changeFigureZOrderUseCase")
    public ChangeFigureZOrderUseCase changeFigureZOrderUseCase() {
        return new ChangeFigureZOrderUseCaseImpl(boardRepository, eventBus);
    }

    @Autowired
    public void setBoardRepository(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Autowired
    public void setFigureRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Autowired
    public void setEventBus(DomainEventBus eventBus) {
        this.eventBus = eventBus;
    }


}
