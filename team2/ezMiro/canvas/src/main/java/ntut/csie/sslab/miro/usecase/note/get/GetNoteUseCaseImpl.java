package ntut.csie.sslab.miro.usecase.note.get;

import ntut.csie.sslab.miro.entity.model.note.Note;
import ntut.csie.sslab.miro.usecase.note.ConvertNoteToDTO;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import java.util.stream.Collectors;

public class GetNoteUseCaseImpl implements GetNoteUseCase, GetNoteInput {
    private FigureRepository figureRepository;
    private String boardId;
    private String noteId;

    public GetNoteUseCaseImpl(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    @Override
    public void execute(GetNoteInput input, GetNoteOutput output) {
        if(input.getBoardId() == null && input.getNoteId() == null) {
            throw new IllegalArgumentException("boardId and noteId cannot be null.");
        }

        if(input.getNoteId() != null) {
            output.setNote(ConvertNoteToDTO.transform(figureRepository.findNoteById(input.getNoteId()).get()));
        } else if (input.getBoardId() != null) {
            output.setNotes(ConvertNoteToDTO.transform(figureRepository.findByBoardId(input.getBoardId()).stream()
                    .filter(f -> f instanceof Note).map(f -> (Note)f).collect(Collectors.toList())));
        }
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public String getNoteId() {
        return noteId;
    }

    @Override
    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}